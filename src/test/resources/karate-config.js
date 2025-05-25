function fn() {
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev'; // default to 'dev' if not set
  }
  var config = {
    appBaseUrl: 'https://jsonplaceholder.typicode.com' // default base URL
  };
  if (env == 'staging') {
    // customize for staging environment
    config.appBaseUrl = 'https://staging-api.example.com';
  } else if (env == 'prod') {
    // customize for production environment
    config.appBaseUrl = 'https://api.example.com';
  }
  // you can add more environment-specific configurations here
  
  // karate.configure('connectTimeout', 5000);
  // karate.configure('readTimeout', 5000);
  
  return config;
}
