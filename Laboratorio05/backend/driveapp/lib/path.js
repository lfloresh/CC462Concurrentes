const path = require('path')
const storage = require('../storage')

const slash = process.platform === 'win32' ? `\\` : '/'

const processPath = (urlPath) => {
  const relativePath = urlPath ? urlPath.replace(/-/g, slash) : slash
  const absolutePath = path.join(storage, relativePath)
  const name = path.dirname(absolutePath).split(slash).pop()
  return { relativePath, absolutePath , name}
};

module.exports = processPath