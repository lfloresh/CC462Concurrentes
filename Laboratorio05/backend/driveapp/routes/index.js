const express = require('express')
const uploadRouter = require('../methods/upload')
const newDirRouter = require('../methods/newDir')
const downloadRouter = require('../methods/download')
const contentRouter = require('../methods/content')
const router =  express.Router()

router.get('/', (req, res) =>{
    res.send("Almacenamiento")
})

router.use('/upload', uploadRouter)

router.use('/newdir', newDirRouter)

router.use('/download', downloadRouter)

router.use('/content', contentRouter)



module.exports = router