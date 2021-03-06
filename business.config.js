const config = require('./metro.serializer')

module.exports = {
  transformer: {
    getTransformOptions: async () => ({
      transform: {
        experimentalImportSupport: false,
        inlineRequires: false,
      },
    }),
  },
  serializer: {
    createModuleIdFactory: config.createModuleIdFactory,
    processModuleFilter: config.processModuleFilter
    /* serializer options */
  }
};
