const Pet = require("../models/Pet");
//helpers
const getToken = require("../helpers/get-token");
const getUserByToken = require("../helpers/get-user-by-token");
const ObjectId = require('mongoose').Types.ObjectId;

module.exports = class PetController {
    //create a pet
    static async create(req, res) {
        const { name, age, weight, color } = req.body;
        const images = req.files;
        const available = true;

        //images upload

        // //validations
        if (!name) {
            return res.status(422).json({ message: "Name is required" });
        }
        if (!age) {
            return res.status(422).json({ message: "Age is required" });
        }
        if (!weight) {
            return res.status(422).json({ message: "Weight is required" });
        }
        if (!color) {
            return res.status(422).json({ message: "Color is required" });
        }
        if(images.length === 0) {
            return res.status(422).json({ message: "Images are required" });
        }

        //get pet owner
        const token = getToken(req);
        const user = await getUserByToken(token);

        //create a pet
        const pet = new Pet({
            name,
            age,
            weight,
            color,
            available,
            images: [],
            user: {
                _id: user._id,
                name: user.name,
                image: user.image,
                phone: user.phone,
            }
        })

        images.map((image) => {
            pet.images.push(image.filename);
        });

        try {
            const newPet = await pet.save();
            res.status(201).json({
                message: "Pet created",
                newPet
            });
        } catch (err) {
            res.status(500).json({ message: err });
        }
    }

    static async getAll(req, res) {
        try {
            const pets = await Pet.find().sort('-createdAt');
            res.status(200).json({pets:pets});
        } catch (err) {
            res.status(500).json({ message: err });
        }
    }

    static async getAllUserPets(req, res) {
        const token = getToken(req);
        const user = await getUserByToken(token);

        try {
            const pets = await Pet.find({ "user._id": user._id }).sort('-createdAt');
            res.status(200).json({pets:pets});
        } catch (err) {
            res.status(500).json({ message: err });
        }
    }

    static async getAllUserAdoptions(req, res) {
        const token = getToken(req);
        const user = await getUserByToken(token);

        try {
            const pets = await Pet.find({ "adopter._id": user._id }).sort('-createdAt');
            res.status(200).json({pets:pets});
        } catch (err) {
            res.status(500).json({ message: err });
        }
    }

    static async getPetById(req, res) {
        const { id } = req.params;

        if (!ObjectId.isValid(id)) {
            return res.status(422).json({ message: "Invalid ID" });
        }

        const pet = await Pet.findOne({ _id: id });

        if (!pet) {
            return res.status(404).json({ message: "Pet not found" });
        }

        res.status(200).json({pet:pet});
    }

    static async removePetById(req, res) {
        const { id } = req.params;

        if (!ObjectId.isValid(id)) {
            return res.status(422).json({ message: "Invalid ID" });
        }

        const pet = await Pet.findOne({ _id: id })

        try {
            if (!pet) {
                return res.status(404).json({ message: "Pet not found" });
            }

            //check if user is the owner of the pet
            const token = getToken(req);
            const user = await getUserByToken(token);

            if (user._id.toString() !== pet.user._id.toString()) {
                return res.status(422).json({ message: "A problem happened, try again later" });
            }

            await Pet.findByIdAndDelete(id);

            res.status(200).json({ message: "Pet removed" });
        } catch (err) {
            res.status(500).json({ message: err });
        }
    }

    static async updatePet(req, res) {
        const { id } = req.params;
        const { name, age, weight, color, available } = req.body;
        const images = req.files;
        const updatedData = {};
        
        //validations
        if (!ObjectId.isValid(id)) {
            return res.status(422).json({ message: "Invalid ID" });
        }

        //check if pet exists
        const pet = await Pet.findOne({ _id: id });

        if (!pet) {
            return res.status(404).json({ message: "Pet not found" });
        }

        //check if user is the owner of the pet
        const token = getToken(req);
        const user = await getUserByToken(token);

        if (user._id.toString() !== pet.user._id.toString()) {
            return res.status(422).json({ message: "A problem happened, try again later" });
        }

        //update pet
        if(!name) {
            return res.status(422).json({ message: "Name is required" });
        } else {
            updatedData.name = name;
        }

        if(!age) {
            return res.status(422).json({ message: "Age is required" });
        } else {
            updatedData.age = age;
        }

        if(!weight) {
            return res.status(422).json({ message: "Weight is required" });
        } else {
            updatedData.weight = weight;
        }

        if(!color) {
            return res.status(422).json({ message: "Color is required" });
        } else {
            updatedData.color = color;
        }

        if(available) {
            updatedData.available = available;
        }

        if(images.length === 0) {
            return res.status(422).json({ message: "Images are required" });
        } else {
            updatedData.images = [];
            images.map((image) => {
                updatedData.images.push(image.filename);
            });
        }

        try {
            await Pet.findByIdAndUpdate(id, updatedData);
            res.status(200).json({ message: "Pet updated"});
        } catch (err) {
            res.status(500).json({ message: err });
        }
    }

    static async schedule(req, res) {
        const { id } = req.params;

        //validations
        if (!ObjectId.isValid(id)) {
            return res.status(422).json({ message: "Invalid ID" });
        }

        //check if pet exists
        const pet = await Pet.findOne({ _id: id });

        if (!pet) {
            return res.status(404).json({ message: "Pet not found" });
        }

        //check if pet is available
        // if (!pet.available) {
        //     return res.status(422).json({ message: "Pet is not available" });
        // }

        //check if user is the owner of the pet
        const token = getToken(req);
        const user = await getUserByToken(token);

        if (pet.user._id.equals(user._id)) {
            return res.status(422).json({ message: "You can't adopt your own pet" });
        }

        //check if user has already scheduled an adoption
        if (pet.adopter) {
            if(pet.adopter._id.equals(user._id)) {
                return res.status(422).json({ message: "You have already scheduled an adoption" });
            }
        }

        //schedule adoption
        pet.adopter = {
            _id: user._id,
            name: user.name,
            image: user.image,
        }

        try {
            await Pet.findByIdAndUpdate(id, pet);
            res.status(200).json({ message: `Adoption scheduled to ${pet.user.name}`});
        } catch (err) {
            res.status(500).json({ message: err });
        }
    }

    static async concludeAdoption(req, res) {
        const { id } = req.params;

        //validations
        if (!ObjectId.isValid(id)) {
            return res.status(422).json({ message: "Invalid ID" });
        }

        //check if pet exists
        const pet = await Pet.findOne({ _id: id });

        if (!pet) {
            return res.status(404).json({ message: "Pet not found" });
        }

        //check if user is the owner of the pet
        const token = getToken(req);
        const user = await getUserByToken(token);

        if (!pet.user._id.equals(user._id)) {
            return res.status(422).json({ message: "You can't conclude an adoption of a pet that is not yours" });
        }

        //conclude adoption
        pet.available = false;

        try {
            await Pet.findByIdAndUpdate(id, pet);
            res.status(200).json({ message: `Congrats! Adoption concluded. ${pet.adopter.name} is the new owner of ${pet.name}`});
        } catch (err) {
            res.status(500).json({ message: err });
        }
    }
}