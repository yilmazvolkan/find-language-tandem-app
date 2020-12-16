# Find Language Tandem App Documentation

 ## SOLID Principles

 I am going to follow the SOLID design principles while developing my product to maintatin software engineering principles with OOP.

 * The Single Responsibility Principle
    * One class should only have one responsibility.
 * The Open Closed Responsibility Principle
    * Software entities such as classes, function, and modules should be open for extension but not modification.
 * The Liskov Substitution Principle 
    * Child classes should never break the parents type definitions.
 * The Interface Segregation Principle
    * No client should be forced to depend on methods it does not use.
 * Dependency Inversion Principle
    * High level modules should not depend on low level modules, both should depend on abstractions. Abstractions should not depend upon details and details should depend upon abstractions.

 ## MVVM Architecture

I am going to follow MVVM architecture to remove tight coupling between each component and apply seperation of concerns. Child classes do not have direct reference to parent, they only have reference by observables. It consists of 3 main components:

 * Model represents data and business logic for the application. Model consists of remote and local data sources, model classes, and repository.
 * View consists of UI elements such as fragments and activities. It listens and sends to user actions to view model, then subscribes the observables from view model to update the UI.
 * View Model is a bridge between view and model. It does not have direct reference to view, therefore view model does not know which view will use the data. It interacts with the model and create observables for the view.

<p float="left">
  <img src="https://github.com/yilmazvolkan/find-language-tandem-app/blob/documentation/docs/architecture.png" height="200">
</p>