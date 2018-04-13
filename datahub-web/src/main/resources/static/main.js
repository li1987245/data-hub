function f() {
    var cons_m3_n_avgpay = -1;
    var cons_m3_n_pay = 0;
    var cons_m3_n_num = 0;
    if (isKeyExists("obj.purchaseDatas.Consumption.month3", obj)) {
        for (var field in obj.purchaseDatas.Consumption.month3) {
            if (typeof(obj.purchaseDatas.Consumption.month3[field].pay) != "undefined" && obj.purchaseDatas.Consumption.month3[field].pay != "" && typeof(obj.purchaseDatas.Consumption.month3[field].num) != "undefined" && obj.purchaseDatas.Consumption.month3[field].num != "" && obj.purchaseDatas.Consumption.month3[field].num != 0) {
                cons_m3_n_pay = cons_m3_n_pay + parseInt(obj.purchaseDatas.Consumption.month3[field].pay);
                cons_m3_n_num = cons_m3_n_num + parseInt(obj.purchaseDatas.Consumption.month3[field].num);
            }
        }
    }
    if (cons_m3_n_num !== 0) {
        cons_m3_n_avgpay = cons_m3_n_pay / cons_m3_n_num;
    }
    return cons_m3_n_avgpay.toFixed(0);
}

const http = require('http');

const hostname = '127.0.0.1';
const port = 3000;

const server = http.createServer((req, res) => {
    res.statusCode = 200;
res.setHeader('Content-Type', 'text/plain');
res.end('Hello World\n');
});

server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});