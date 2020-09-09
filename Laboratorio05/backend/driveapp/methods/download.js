const router = require('express').Router()
const fs = require('fs')
const fspromises = require('fs').promises
const mime = require('mime-types')
const processPath = require('../lib/path')
var archiver = require('archiver')

router.get('/:path', async (req, res) => {
        var file = processPath(req.params.path).absolutePath;
        var dirname = processPath(req.params.path).relativePath;
        const stat = await fspromises.lstat(file);

        if(stat.isDirectory()){
            
            const output = fs.createWriteStream(`${process.env.HOME_TEMP}\\${dirname}.zip`)
            var archive = archiver('zip'); 
            output.on('close', function () { 
            console.log(archive.pointer() + ' total bytes'); 
            console.log('archiver has been finalized and the output file descriptor has closed.'); 
            }); 

            archive.on('error', function(err){ 
                throw err; 
            }); 

            archive.pipe(output); 
            archive.directory(file ,false)
            archive.finalize();
            file = `${process.env.HOME_TEMP}\\${dirname}.zip`
        } 
        
        const mimetype = mime.lookup(file);
        console.log(mimetype);
        res.setHeader('Content-Disposition', `attachment; filename=${file}`);
        res.setHeader('Content-Type', mimetype);
        res.download(file,function (err) {
            if (err) {
                console.log(err); // Check error if you want
              }
           
            if (mimetype == "application/zip"){
                fs.unlink(file, function(){
                    console.log("File was deleted") // Callback
                })
            }
        })
        
});

module.exports = router;