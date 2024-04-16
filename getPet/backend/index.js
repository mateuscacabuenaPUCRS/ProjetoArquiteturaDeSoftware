const express = require('express');
const cors = require('cors');

const app = express();

//Config JSON response
app.use(express.json());

//Config CORS
app.use(cors({ credentials: true,  origin: 'https://localhost:3000'}));

//Routes
const UserRoutes = require('./routes/UserRoutes')
const PetRoutes = require('./routes/PetRoutes')

app.use('/users', UserRoutes)
app.use('/pets', PetRoutes)

app.listen(5000)