Java MNAV Autopilot
===================

Description
-----------

This project aims to create a state of the art autopilot for use with the MNAV
Device simulation as used in CRRCSim

Using only the provided sensors, namely:
* Accelerometer
* Gyroscopes
* Pitot and normalized pressure gauge
* GPS

The simulated sensor is of course more reliable then a real life version, however
many challenges remain in filtering and modeling the aircraft, and so it serves
as an excellent starting point for actual drone creation.

Install
-------

You need to install and set up CRRCSim for MNav control. For development, Netbeans
is recommended as I provide project files and uses the integrated test system.
For my system, I use an USB Xbox controller to provide input for the autopilot,
so some kind of joystick or input device may be useful.

Links
-----
CRRCSim - http://sourceforge.net/apps/mediawiki/crrcsim/index.php?title=Main_Page

JXInput - http://www.hardcode.de/jxinput/

Goal
----

This autopilot is intended to provide an intelligent approach to glider flying,
relying on detecting and using Thermic areas (Vertical winds) to maintain and
even attain altitude.

Developer
---------
So far, it's just me doing spare time work on this project :-) Feel free to jump in.

