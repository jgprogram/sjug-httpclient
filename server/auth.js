const auth = require('basic-auth')
const userName = 'user'
const userPass = 'pass'

module.exports = function (request, response, next) {
    const user = auth(request)
    if(!user || user.name !== userName || user.pass !== userPass) {
        response.set('WWW-Authenticate', 'Basic realm="localhost"')

        return response.status(401).send()
    }

    return next()
}