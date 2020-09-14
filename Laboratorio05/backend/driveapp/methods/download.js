const router = require('express').Router()
const fs = require('fs')
const fsmove = require('fs-extra').move
const fspromises = require('fs').promises
const mime = require('mime-types')
const processPath = require('../lib/path')
const AdmZip = require('adm-zip')
const slash = process.platform === 'win32' ? `\\` : '/'

router.get('/:path', async (req, res) => {
        var file = processPath(req.params.path).absolutePath;
        var dirname = processPath(req.params.path).name;

        const stat = await fspromises.lstat(file);
        
        if(stat.isDirectory()){
            
            const uploadDir = fs.readdirSync(file)
            const zip = new AdmZip()
            
            for(var i = 0; i < uploadDir.length; i++){
                zip.addLocalFile(file+slash+uploadDir[i])
                //zip.writeZip(`${process.env.HOME_TEMP}` + slash + `${dow}`)
            }
            
            const downloadName = `${Date.now()}.zip`
            const data = zip.toBuffer()
            zip.writeZip(`${process.env.HOME_TEMP}` + slash + `${downloadName}`)
            res.set('Content-Type', 'application/octet-stream');
            res.set('Content-Disposition', `attachment; filename=${downloadName}`)
            res.set('Content-Length', data.length)
            res.send(data)
        } else{
            const mimetype = mime.lookup(file);
            res.setHeader('Content-Disposition', `attachment; filename=${file}`);
            res.setHeader('Content-Type', mimetype);
            res.download(file,function (err) {
                if (err) {
                    console.log(err); // Check error if you want
                }

                
            })

        }
        
      
        
});

module.exports = router;