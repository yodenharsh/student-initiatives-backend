# How to Run the Backend

### Requiremnts

- JDK 17+
- Any IDE with Java EE support
- MySQL installation
- Internet connection (first time build only)

### Steps

1. clone the project
2. In `/student-initiatives-backend/src/main/resources/application.properties` ,change the credentials to your MySQL details
3. Run the Schema generator.sql file to generate the database
4. Run the `StudentInitiativesBackendApplication.java` at the root of `com.woxsen.studentinitiatives` package.
5. The application will run on localhost:8080 by default and all APIs can be accessed from there.

# API Description

### References -

Club -

```
{

"clubId": number,
"clubName": string,
"presidentName": string,
"vicePresidentName": string,
"mission": string,
"vision": string,

}
```

COE -

```
{
    "id": number,
    "user": {
        "email": string
    },
    "mentors": string,
    "mission": string,
    "vision": string
}
```

School -

```
{
    "schoolId": number,
    "schoolName" : string
}
```

## Authentication-related

<table>
<tr>
<th>URL</th>
<th>Method</th>
<th>Consumes</th>
<th>Produces</th>
<th>Description</th>
</tr>
<tr>
<td>/api/user/login</td>
<td>POST</td>
<td><p>JSON format - { </p>
<p>"email" : string</p>
<p>"password" : string</p>
</td>
<td><p>JSON Format -</p>
<p>{clubId: string}</p>
<p>OR</p>
<p>{coeId: string}</p>
</td>
</td>
<td>Provided that the email-password combination is correct, returns a json containing either the clubId associated with it the account or the coeId associated with the account</td>
</tr>
<tr>
<td>/api/user/</td>
<td>POST</p>
<td>JSON.
<p>Format - </p>
<p>{</p>
<p>"email" : string</p>
<p>"password" : string</p>
<p>}</p>
</td>

<td>JSON format - {"success": string}</td>
<td>Adds a new user and returns success: "true" or success: "false"</td>
</tr>
<tr>
<td>/api/user/</td>
<td>DELETE</td>
<td><p>JSON Format - </p>
<p>{</p>
<p>"success": string
<p>}</p>
</td>
<td>JSON with success: "true" or "false"</td>
<td>Deletes the user with those credentials AND the club associated with that user</td>
</tr>
</table>

## School-related

<table>
<tr>
<th>URL</th>
<th>Method</th>
<th>Consumes</th>
<th>Produces</th>
<th>Description</th>
</tr>
<tr>
<td>/api/school/</td>
<td>GET</td>
<td>Nothing</td>
<td><p>JSON</p>
<p>JSON format - School[] </p></td>
<td>Gets the list of all schools available in the DB</td>
</tr>
<tr>
<td>/api/school/{schoolId}/clubs</td>
<td>GET</td>
<td>The {schoolId} path variable</td>
<td><p>JSON Format - </p>
<p>Club[]</td>
<td>Get the list of all the clubs associated with that schoolId</td>
</tr>
<tr>
<td>/api/school/{schoolId}</td>
<td>GET</td>
<td>The {schoolId} path variable</td>
<td>JSON Format - School</td>
<td>Gets info on the specific school</td>
</tr>
<tr>
<td>/api/school/</td>
<td>POST</td>
<td><p>JSON Format - </p>
<p>{ "schoolName" : string }</p>
</td>
<td>JSON format {"success":string}</td>
<td>Add a school to the database and return success ("true" or "false")</td>
</tr>
<tr>
<td>/api/school/{schoolId}</td>
<td>DELETE</td>
<td>The {schoolId} path variable</td>
<td>JSON format - {"success":string}</td>
<td>Delete a school from DB by it's ID and returns success: "true" or "false"</td>
</tr>
</table>

## Club-related

<table>
<tr>
<th>URL</th>
<th>Method</th>
<th>Consumes</th>
<th>Produces</th>
<th>Description</th>
</tr>
<tr>
<td>/api/club/</td>
<td>GET</td>
<td>Nothing</td>
<td>JSON Format - Club[]</td>
<td>Returns list of all clubs</td>
</tr>
<tr>
<td>/api/club/</td>
<td>POST</td>
<td>JSON Format -
<p>{</p>
<p>"clubName": string, </p>
<p>"presidentName": string,</p>
<p>"vicePresidentName": string,</p>
<p>"mission":string | null,</p>
<p>"vision": string | null,</p>
<p>"email": string,</p>
<p>"schoolId": number | null</p> 
<p>}</p>
</td>
<td>JSON format - { "clubId": number }</td>
<td>Adds the given club to the DB and returns the generated clubId. The email and school with given schoolId must already exist in the DB.
</tr>
<tr>
<td>/api/club/{clubId}</td>
<td>GET</td>
<td>The path variable {clubId}</td>
<td>JSON Format - Club</td>
<td>Gets info on the specific club associated with the given clubId</td>
</tr>
<tr>
<td>/api/club/{clubId}</td>
<td>DELETE</td>
<td>The path variable {clubId}</td>
<td>JSON format - {"success": string}</td>
<td>Deletes club with the given clubId and returns success: "true" or "false"</td>
</tr>
<tr>
<td>/api/club/{clubId}/image/{type}</td>
<td>GET</td>
<td>The {clubId} and {type} path variables. Type can only be "logos", "president" or "vice-president"</td>
<td>A JPEG,JPG or PNG image</td>
<td>Returns an image of the club associated with the given clubId. Image is of the president, vice-president or the club logo.</td>
</tr>
<tr>
<td>/api/club/{clubId}/image/{type}</td>
<td>POST</td>
<td>The {clubId} and {type} path variables and a form-data as request body. Form-data should have a parameter "image" with the actual image (in JPEG,JPG or PNG format) attached as value. {type} can only be one of ["logos","president", "vice-president"]</td>
<td>JSON format - {success: string}</td>
<td>Adds the club logo, president or vice-president of the given club and returns success: "true" or "false"</td>
</table>

## Club Events

<table>
<tr>
<th>URL</th>
<th>Method</th>
<th>Consumes</th>
<th>Produces</th>
<th>Description</th>
</tr>
<tr>
<td>/api/event</td>
<td>GET</td>
<td>Nothing</td>
<td>
JSON format - Event[]
</td>
<td>Gets a list of all events available in the database</td>
</tr>
<tr>
<td>/api/event/{eventId}</td>
<td>GET</td>
<td>The {eventId} path variable</td>
<td>JSON format - Event</td>
<td>Gets complete information on the given club</td>
</tr>
<tr>
<td>/api/event/club/{clubId}</td>
<td>GET</td>
<td>The {clubId} path variable</td>
<td>JSON format - Event[]</td>
<td>Gets a list of all event associated with the club</td>
</tr>
<tr>
<td>/api/event</td>
<td>POST</td>
<td><p>JSON Format -</p>
<p>{</p>
<p>"title": string</p>
<p>"eventDesc": string</p>
<p>"clubId": number</p>
<p>"date": "yyyy-MM-dd HH:mm:ss"</p>
<p>}</p>
</td>
<td>JSON Format - { "eventId": number }</td>
<td>Adds the given event to DB. 
</tr>
<tr>
<td>/api/event/{eventId}</td>
<td>DELETE</td>
<td>The {eventId} path variable</td>
<td>JSON format - {success: string}</td>
<td>Deletes the given event from the DB and returns success: "true" or "false"</td>
</tr>
<tr>
<td>/api/event/{eventId}</td>
<td>PATCH</td>
<td>The {eventId} path variable and JSON format - 
<p>{</p>
<p>"title": string</p>
<p>"eventDesc": string</p>
<p>"clubId": number</p>
<p>"date": "yyyy-MM-dd HH:mm:ss"</p>
<p>}</p>
</td>
<td>JSON format - {"success" : string}</td>
<td>Updates the event associated with given event ID and returns success: "true" or "false"</td>
</tr>
<tr>
<td>/api/event/image/{clubId}/{eventId}</td>
<td>GET</td>
<td>The {clubId} and {eventId} path variables</td>
<td>Image in .JPG, .JPEG or .PNG format</td>
<td>Gets image for the given eventId and clubId</td>
</tr>
<tr>
<td>/api/event/image/{clubId}/{eventId}</td>
<td>PUT</td>
<td>The {clubId} and {eventId} path variables and a form-data as request body. Form-data should have a parameter "image" with the actual image (in JPEG,JPG or PNG format) attached as value</td>
<td>JSON format - {"success" : string}</td>
<td>Saves image and returns success = "true" or "false"</td>
</tr>
</table>

## COE
