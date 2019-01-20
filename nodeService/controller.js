'use strict';

exports.index = function(req, res) {
    res.send("Done n dusted!");
  };

exports.getReading = function(request, response) {
    const superagent = require('superagent');

    superagent.get('http://192.168.1.102/read')
    .end((err, res) => {
        if (err) { return console.log(err); }

        console.log(res.text);
        
        response.send(res.text);
    })    
}

exports.loop = async function(req,res){
    const Composer = require('./composer');
    const superagent = require('superagent');
    const fs = require('fs');
    var obj = await JSON.parse(fs.readFileSync('config.json', 'utf8')).IPs;

    setInterval(() => {
        obj.forEach(element => {
            let ip = element.ip;
            let plot = element.plot;

            superagent.get('http://'+ip+'/read')
            .end((err, res) => {
                if (err) { return console.log(err); }

                console.log(res.text);
                let val = res.text;

                let network = new Composer();
                let updateStat = network.updatePlot(plot, val);
            })
        });
    }, 180000)
}

exports.test = function(req,res){
    var Composer = require('./composer');

    let network = new Composer(); 
    //let init = network.init();

    //let data = network.listTitles();

    let updateStat = network.updatePlot('testPlot', 0.1994);

    res.send(updateStat);
}
