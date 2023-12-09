import config from '../config'

export default {
 getApiBaseUrl() {
    const env = this.getProcessEnv() || 'local';
    const target = config[env];
    return target.api.baseUrl;

 },
  getProcessEnv() {
    return process.env.NODE_ENV.trim();
  },

  localDateTimeToYearMonthDay(value) {
    const date = new Date(value);

    const year = date.getFullYear();
    let month = date.getMonth() + 1;
    if (month < 10) month = "0" + month;

    let day = date.getDate();
    if (day < 10) day = "0" + day;


    return `${year}-${month}-${day}`
  },

  localDateTimeToYearMonthDayHourMinutes(value) {
    const date = new Date(value);

    const year = date.getFullYear();
    let month = date.getMonth() + 1;
    if (month < 10) month = "0" + month;

    let day = date.getDate();
    if (day < 10) day = "0" + day;

    const hours = date.getHours();
    const minutes = date.getMinutes();


    return `${year}-${month}-${day}, ${hours}:${minutes}`
  }

}