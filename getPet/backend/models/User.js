const mongoose = require("../db/conn");
const { Schema } = mongoose;

const User = mongoose.model(
  "User",
  new Schema(
    {
      name: { type: String, required: true },
      email: { type: String, required: true },
      password: { type: String, required: true },
      phone: { type: String, required: true },
      image: String,
    },
    { timestamps: true } //created_at and updated_at
  )
);

module.exports = User;
