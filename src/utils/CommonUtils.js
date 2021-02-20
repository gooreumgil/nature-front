import config from '../config'

export default {
 getApiBaseUrl() {
    const env = this.getProcessEnv() || 'local';
    const target = config[env];
    return target.api.baseUrl;

 },
  getProcessEnv() {
    return process.env.NODE_ENV.trim();
  }
}