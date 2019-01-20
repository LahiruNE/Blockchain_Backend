var express = require('express'),
  app = express(),
  port = process.env.PORT || 3002;

app.listen(port);

var controller = require('./controller');

app.route('/index')
    .get(controller.index)

app.route('/read')
    .get(controller.getReading)

app.route('/test')
    .get(controller.test)

app.listen(3001, function () {
    controller.loop();
});

app.use(function(req, res) {
    res.status(404).send({url: req.originalUrl + ' not found'})
});

console.log('Jara RESTful API server started on: ' + port);
