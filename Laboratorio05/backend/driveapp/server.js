const express = require('express')
const cors = require('cors')
const routes = require('./routes/index');
const app = express()

app.use(express.json());
app.use(cors())
if(process.env.NODE_ENV === 'development'){
    app.use(cors('dev'))
}
app.use(routes)

const PORT = process.env.PORT || 5000

app.listen(PORT, console.log(`Server runing in ${process.env.NODE_ENV}on port ${PORT}`))

