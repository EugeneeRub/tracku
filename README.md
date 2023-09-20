# TrackU
(Not active)

![TrackU logo](images/pic0.png)

Prototype of the project for tracking the users geolocation.\
Uses Firebase Realtime Database to save the location of created users, while they are active.
Mapbox is used like map provider.

**Project is old**. \
Here is used the MVP architecture, ButterKnife used like a view provider.

| Sign in              | Sign up              | Main                       |
|----------------------|----------------------|----------------------------|
| ![](images/pic1.png) | ![](images/pic2.png) | ![](images/pic3.png)       |
| Sign in screen       | Sign up screen       | Main screen on the startup |

| User picker list                                                            | Users phones               | Users info edit                                 |
|-----------------------------------------------------------------------------|----------------------------|-------------------------------------------------|
| ![](images/pic4.png)                                                        | ![](images/pic5.png)       | ![](images/pic6.png)                            |
| Observer can pick any user and start listening location if he/she is active | Observer can call any user | If required observer can edit users information |

| Users pop-up                                   | Active user           | Transferring location                 |
|------------------------------------------------|-----------------------|---------------------------------------|
| ![](images/pic7.png)                           | ![](images/pic8.png)  | ![](images/pic9.png)                  |
| List of all users that can be found on the map | Active user observing | Transferring location to the observer |
