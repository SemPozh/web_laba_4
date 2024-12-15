module.exports = {
    devServer: {
        proxy: {
            "/backend/api": {
                target: "http://localhost:8080",
                changeOrigin: true,
                pathRewrite: { '^/backend/api': '' },
            },
        },
        public: 'http://0.0.0.0:8080',
        https: false,
        allowedHosts: ['ubuntu']
    }
};