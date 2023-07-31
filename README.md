[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/x6ckGcN8)
# 3500 PA05 Project Repo

[PA Write Up](https://markefontenot.notion.site/PA-05-8263d28a81a7473d8372c6579abd6481)


**PRODUCT PITCH**

Looking for a way to simplify that endless to-do list? Overwhelmed by the endless options and customization features of apps like Notion?
The team at 5NAFontenot's is excited to announce the Beta release of BujoApp - an organization tool with clean, easy-to-understand UI.

With BujoApp, you can create Tasks and Events to represent various commitments throughout your week. You'll never be late to an appointment
again with Event time and duration information, and enjoy checklist satisfaction with completion boxes for each Task on your list.
Optionally, you can add descriptions with HTTP/HTTPS links in the description field for both Events and Tasks - perfect to keep track of Evite
links, recipes, or links to images. A variety of information is available at a glance with BujoApp's quick-view Progress Bars, tallying total
Events and Tasks, % completed Tasks, and the breakdown of each day's % completed Tasks.

Users can personalize their BujoApp through various pre-made themes and endless custom backgrounds. Additionally, BujoApp features a Quotes
and Notes pad perfect for grocery lists, shower thoughts, or phone numbers. Trying to simplify your week? BujoApp allows users to cap their
Events and Tasks at a customizable number, promoting a balanced and healthy life.

All of BujoApp's abilities are easily accessible through the menu bar, or through simple keyboard shortcuts. Finally, BujoApp is here to stay! 
Customized and completed Weeks can be saved to your computer and opened in the future, to edit or look back fondly upon.


**APP SCREENSHOTS**

![Screenshot 2023-06-21 at 7 20 09 PM](https://github.com/CS-3500-OOD/pa05-olivia-stone-melody/assets/124623038/4bcc990c-4822-43d7-8c49-ba83b0f59aa1)
_BujoApp Homescreen GUI_

![Screenshot 2023-06-21 at 7 11 23 PM](https://github.com/CS-3500-OOD/pa05-olivia-stone-melody/assets/124623038/fa372d31-3f36-476b-870c-025abd158f9a)
_BujoApp New Event dialog_

![Screenshot 2023-06-21 at 7 20 21 PM](https://github.com/CS-3500-OOD/pa05-olivia-stone-melody/assets/124623038/e818b26d-4c29-4da6-a9f2-970a8b94645f)
_BujoApp Settings dialog_


**SOLID PRINCIPLES**

_S: Single Responsibility Principle_
We followed Single Responsibility by designing classes in the Model to represent and retain information about distinct components
of the bullet journal. For example - a Spread is broken into a Week, which contains Weekdays, where Events and Tasks are kept track of.
Each of the classes that makes up a Spread can be "recusively" reached through the Spread (as with encoding and decoding), but are ultimately
responsible for their own updating.

_O: Open-Closed Principle_
We followed Open-Closed by designing an abstract WeekItems class to allow for future WeekItems to be created with different purposes and
functionality from the existing Events and Tasks without modifying the existing functionality. Generally, we also defined interfaces for the 
Control and View, allowing for a future implementation of the BujoApp to promise the same general functionality but implement differently.

_L: Liskov Substitution Principle_
We followed Liskov Substitution also through the design of our abstract WeekItems class. No methods or fields are promised by WeekItems
that are not implemented or relevant to the subclasses, Event and Task. 

_I: Interface Segregation Principle_
We followed Interface Segregation by ensuring that any client of one of our interfaces implemented full functionality for that interface.
This principle apparent in our ActionResponder, BujoController, and BujoView interfaces and their concrete implementations.

_D: Dependency Inversion Principle_
We followed Dependency Inversion by making the View constructor take in an object of type ActionResponder, and program our concrete controller
class to implement that interface. In doing this, we expanded the functionality of our controller to respond to different user actions while
minimizing the controller functionality that the view can access in it's methods.


**FUTURE EXTENSION**

There are a number of ways our BujoApp could be updated or extended in future releases. The Theme enum allows the pre-made themes to be expanded.
This would require simple additions - of the name of the new theme to the enum class, and of that theme's font and colors to the associated lists.
Another way BujoApp could be exended is through the addition of new WeekItems like Idea, Appointment, or Holiday. This is possible because of the
abstracted class. To implement new WeekItems, a new class would need to be designed and button added to the menu bar, but it would be relatively
straighforward to add a new branch to the ActionResponder respond method and fill in associated helpers.


**ATTRIBUTION**

BujoApp was created using SceneBuilder and Intellij. 5NAFontenot's did not use any images from sources that need to be attributed.
