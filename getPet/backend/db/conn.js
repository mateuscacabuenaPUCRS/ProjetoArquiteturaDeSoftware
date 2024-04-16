const mongoose = require('mongoose');

async function main() {
  await mongoose.connect('mongodb://localhost:27017/getapet');
  console.log('Connected to Mongoose');
}

main().catch(err => console.error(err));

module.exports = mongoose;