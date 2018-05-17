const proxyTarget = 'http://127.0.0.1:8088';
module.exports = {
    devServer:{
        proxy:{
            '/**':{
                target: proxyTarget,
                changeOrigin: true,
                bypass: function(req, res, proxyOpt){
                    //添加HTTP header 标识Proxy 开启 为了跨域请求
                    res.set('X-ICE-PROXY','on'),
                    res.set('X-ICE-PROXY-BY',proxyTarget)
                }
            }
        }
    }
}