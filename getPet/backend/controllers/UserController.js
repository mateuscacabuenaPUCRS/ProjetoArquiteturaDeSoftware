const User = require("../models/User");

const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
//helpers
const createUserToken = require("../helpers/create-user-token");
const getToken = require("../helpers/get-token");
const getUserByToken = require("../helpers/get-user-by-token");

module.exports = class UserController {
  static async register(req, res) {
    const { name, email, password, phone, confirmpassword } = req.body;

    //validations
    if (!name) {
      return res.status(422).json({ message: "Name is required" });
    }
    if (!email) {
      return res.status(422).json({ message: "Email is required" });
    }
    if (!phone) {
      return res.status(422).json({ message: "Phone is required" });
    }
    if (!password) {
      return res.status(422).json({ message: "Password is required" });
    }
    if (!confirmpassword) {
      return res.status(422).json({ message: "Confirm Password is required" });
    }
    if (password !== confirmpassword) {
      return res.status(422).json({ message: "Passwords do not match" });
    }

    //check if user exists
    const userExists = await User.findOne({ email });

    if (userExists) {
      return res.status(409).json({ message: "User already exists" });
    }

    //create a password
    const salt = await bcrypt.genSalt(12);
    const passwordHash = await bcrypt.hash(password, salt);

    //create a user
    const user = new User({
      name,
      email,
      password: passwordHash,
      phone,
    });

    try {
      const newUser = await user.save();

      await createUserToken(newUser, req, res);
    } catch (err) {
      res.status(500).json({ message: err });
    }
  }

  static async login(req, res) {
    const { email, password } = req.body;

    //validations
    if (!email) {
      return res.status(422).json({ message: "Email is required" });
    }
    if (!password) {
      return res.status(422).json({ message: "Password is required" });
    }

    //check if user exists
    const user = await User.findOne({ email });

    if (!user) {
      return res.status(404).json({ message: "User not found" });
    }

    //check if password is correct
    const isMatch = await bcrypt.compare(password, user.password);

    if (!isMatch) {
      return res.status(401).json({ message: "Invalid credentials" });
    }

    await createUserToken(user, req, res);
  }

  static async checkUser(req, res) {
    let currentUser;

    if (req.headers.authorization) {
      const token = getToken(req);
      const decoded = jwt.verify(token, "nossosecret");

      currentUser = await User.findById(decoded.id);
      currentUser.password = undefined;
    } else {
      currentUser = null;
    }

    res.status(200).send(currentUser);
  }

  static async getUserById(req, res) {
    const { id } = req.params;
    const user = await User.findById(id).select("-password");

    if (!user) {
      return res.status(404).json({ message: "User not found" });
    }

    res.status(200).json({ user });
  }

  static async editUser(req, res) {
    const { id } = req.params;
    const token = getToken(req);
    const user = await getUserByToken(token);

    const { name, email, phone, password, confirmpassword } = req.body;

    if(req.file) {
      user.image = req.file.filename;
    }

    if (!name) {
      return res.status(422).json({ message: "Name is required" });
    }
    user.name = name;

    if (user.email !== email && userExists) {
      return res.status(422).json({ message: "Use another email" });
    }
    user.email = email;

    if (!phone) {
      return res.status(422).json({ message: "Phone is required" });
    }
    user.phone = phone;

    if(password != confirmpassword) {
      return res.status(422).json({ message: "Passwords do not match" });
    } else if(password === confirmpassword && password != null) {
      const salt = await bcrypt.genSalt(12);
      const passwordHash = await bcrypt.hash(password, salt);
      user.password = passwordHash;
    }

    try {
      const updatedUser = await user.save();
      return res.status(200).json({ message: "User updated successfully", user: updatedUser });
    } catch (err) {
      return res.status(500).json({ message: err });
    }
  }
};