require('dotenv').config()

const storage = process.env.HOME_STORAGE

if (!storage) {
  console.error(
    'Storage path not defined,',
    'set a value for HOME_STORAGE environment variable'
  );
  process.exit(1)
}

module.exports = storage