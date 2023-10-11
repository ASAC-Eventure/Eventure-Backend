# Eventure-Backend!
# Eventure - Event Booking and Management System

Eventure is a user-friendly system that allows users to sign up, log in, create and edit their profiles, browse, book or cancel event bookings, and search for events in their country or on specific dates. Users can also add their own events to the system, and after admin approval, these events will be available for booking. Eventure sends email notifications to users when they book or cancel events. Additionally, the system provides an admin dashboard for managing users, events, statistics, and total income.

## Prerequisites

Eventure is built using the Java Spring framework, which includes various dependencies, such as Lombok, Spring Data JPA, Hibernate, Flyway Migration, Spring Security, and Thymeleaf for the front end. Ensure you have these dependencies set up before running the project.

## Configuration

Eventure is configured to allow all users to add, browse, and search for events. However, only authorized users can book or cancel events, view their profile pages, and access the "My Events" page, which contains all their booked events. Admins, as stored in the database, can access the admin dashboard.

## Database

Eventure uses the PostgreSQL database management system and includes eight tables:

1. `Address`: Stores event addresses.
2. `Address_country`: Maps events to specific countries.
3. `Event`: Stores booked events from the API.
4. `Events_requested_to_add`: Stores events added by users pending admin approval.
5. `Eventure_users`: Stores user data for registered users.
6. `Flyway_schema_history`: Manages database schema history for versioning.
7. `Location`: Stores venue names for events.
8. `Role_types`: Defines user roles as 'user' or 'admin'.

Relationships:
- One-to-Many: A user can book many events, one event can have many users, and one user can add many events.
- One-to-One: Relationships exist between `country`, `address`, and `location`. Each event is added by one user, and a user can have only one role.

## API Documentation

Eventure fetches event data from the [Jambase API](https://www.jambase.com/jb-api/v1/events?apikey=357b5a27-55f2-487b-9b1c-83f6ad689c3e) to provide event information. The API is integrated into the Eventure backend via the Event Service, which retrieves necessary data for the system. The Jambase API offers search endpoints for countries and start dates.

## Security

Eventure uses Spring validation tools to validate user input. These validations include checking for duplicate usernames during sign-up, ensuring passwords meet specific criteria, and confirming that passwords match the confirm password field. Additionally, Eventure includes validation for the "Add Event" form to prevent users from adding duplicate events and to validate date fields.

## Wireframes

### User Interface
![wireframeNew.png](..%2F..%2FDownloads%2FwireframeNew.png)
### Admin Dashboard
![image.png](..%2F..%2FDownloads%2Fimage.png)
### View Booked Events
![image (7).png](..%2F..%2FDownloads%2Fimage%20%287%29.png)
### View Unpaid Events
![image (6).png](..%2F..%2FDownloads%2Fimage%20%286%29.png)
### View Pending Events
![image (2).png](..%2F..%2FDownloads%2Fimage%20%282%29.png)
### View Paid Events
![image (3).png](..%2F..%2FDownloads%2Fimage%20%283%29.png)
### View Cancelled Events
![image (1).png](..%2F..%2FDownloads%2Fimage%20%281%29.png)
### View Requested Events
![image (5).png](..%2F..%2FDownloads%2Fimage%20%285%29.png)
### View Statistics
![image (4).png](..%2F..%2FDownloads%2Fimage%20%284%29.png)
### View Total Income
![image (8).png](..%2F..%2FDownloads%2Fimage%20%288%29.png)










