export function parseApiResponse(response){
    var body = response.data;
    var parsedBody = {};
    for(const [key, value] of Object.entries(body)){
        parsedBody[key] = JSON.parse(value)
    }
    return parsedBody; 
}
