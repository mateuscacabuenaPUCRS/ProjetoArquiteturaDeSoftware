const mongoose = require("../db/conn");
const { Schema } = mongoose;

const Pet = mongoose.model(
  "Pet",
  new Schema(
    {
      name: { type: String, required: true },
      age: { type: Number, required: true },
      weight: { type: Number, required: true },
      color: { type: String, required: true },
      images: { type: Array, required: true },
      available: Boolean,
      user: Object,
      adopter: Object
    },
    { timestamps: true } //created_at and updated_at
  )
);

module.exports = Pet;