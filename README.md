# Bhojan-Pani

> A comprehensive cloud kitchen platform built with Spring Boot, enabling seamless food ordering and management with enterprise-grade features.

## 🌟 Overview

Bhojan-Pani is a robust backend-focused cloud kitchen management system that provides complete food ordering capabilities. The platform supports multi-role access control, payment processing, and automated email notifications, making it ideal for cloud kitchen operations.

## ✨ Key Features

### Admin Capabilities
- **Food Management**: Complete CRUD operations for food items and variants
- **Category Management**: Organize food items with comprehensive category system
- **Role & Permission System**: Granular access control with default roles and custom permissions
- **User Management**: Assign roles and permissions to users
- **Order Management**: Track and manage all customer orders
- **Order Configuration**: Set tax rates and discount values with system defaults
- **Payment Control**: Configure maximum payment attempts per order (default: 2 attempts)
- **Geographic Data Management**: Load countries, states, cities, regions, and timezones from CSV files

### Customer Features
- **User Authentication**: Secure signup/login with JWT token-based authentication
- **Address Management**: Multiple address support per user
- **Shopping Cart**: Add food items and variants to cart with cart items management
- **Order Processing**: Place orders with integrated payment system
- **Payment Integration**: Secure payments via Razorpay gateway with retry mechanism
- **Email Notifications**: Automated order confirmation, payment, and cancellation emails

### System Features
- **CSV Data Import**: Load global geographic data (countries, states, cities, regions, timezones, subregions)
- **Image Storage**: AWS S3 integration for efficient image management
- **Database Migrations**: Flyway for version-controlled database changes
- **Pagination**: Efficient data retrieval with pagination support
- **Exception Handling**: Global exception handler with custom exceptions
- **Validation**: Custom validators, Jakarta validation annotations, and enum-based validations
- **DTO Pattern**: Clean request/response handling with MapStruct mapping
- **Batch Processing**: Optimized database operations with batch processing

## 🛠️ Tech Stack

### Backend
- **Java Spring Boot** - Core framework
- **Spring Security** - Authentication & authorization
- **JWT Tokens** - Stateless authentication
- **MySQL** - Primary database
- **Flyway** - Database migration management
- **MapStruct** - Object mapping

### External Services
- **AWS S3** - Image storage
- **Razorpay** - Payment gateway
- **JavaMail** - Email service

### Frontend
- **HTML/CSS** - Payment interface (minimal frontend implementation)

## 🗄️ Database Schema & Relationships

### Entity Relationships
- **User ↔ Role**: One-to-One relationship
- **Role ↔ Permissions**: Many-to-Many relationship
- **User ↔ Addresses**: One-to-Many relationship (one user can have multiple addresses)
- **Cart ↔ Cart Items**: One-to-Many relationship
- **Cart ↔ Orders**: One-to-Many relationship
- **Category ↔ Foods**: One-to-Many relationship (one category contains multiple foods)
- **Food ↔ Food Variants**: One-to-Many relationship
- **Order ↔ Payments**: One-to-Many relationship (support for multiple payment attempts)

### Geographic Data Structure
- **Country ↔ States**: One-to-Many relationship
- **Country ↔ Timezones**: One-to-Many relationship
- **State ↔ Cities**: One-to-Many relationship
- **Region ↔ Subregions**: One-to-Many relationship

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- AWS Account (for S3)
- Razorpay Account

### Environment Variables
Create a `.env` file or set the following environment variables:

```env
# Database Configuration
DATABASE_HOST=localhost
DATABASE_PORT=3306
DATABASE_NAME=hrms
DB_UNAME=your_db_username
DB_PASS=your_db_password

# JWT Configuration
SECRET_KEY=your_jwt_secret_key

# AWS S3 Configuration
AWS_ACCESS_KEY=your_aws_access_key
AWS_SECRET_KEY=your_aws_secret_key

# Razorpay Configuration
RAZORPAY_KEY=your_razorpay_key_id
RAZORPAY_SECRET=your_razorpay_secret

# Email Configuration
EMAIL=your_email@example.com
EMAIL_PASSWORD=your_email_password
```

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Aashish1226/bhojan-pani.git
   ```

2. **Set up MySQL database**
   ```sql
   CREATE DATABASE Foodies;
   ```

3. **Configure environment variables**
    - Set up your environment variables as shown above

4. **Import geographic data**
    - CSV files for countries, states, cities, regions, and timezones are provided with the project
    - Simply run the application - Geographic data can be imported via the available API endpoint

5. **Run the application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

6. **Access the application**
    - API Base URL: `http://localhost:8083`
    - Payment Interface: `http://localhost:8083/api/payment`

## 📁 Project Structure

```
src/main/java/com/bhojanpani/
├── config/          # Configuration classes
├── Controller/      # REST controllers
├── DTO/            # Data Transfer Objects
├── Entity/         # JPA entities
├── Enum/          # Application enums
├── Exceptions/      # Custom exceptions & global handler
├── Mapper/         # MapStruct mappers
├── Repository/     # JPA repositories
├── service/        # Business logic services
├── validation/      # Custom validators
└── Util/       # Utility Classes
```

## 🔐 Authentication & Authorization

The system implements a comprehensive role-based access control:

- **JWT Token Authentication**: Stateless authentication mechanism
- **Default Roles**: Pre-configured admin and user roles
- **Custom Permissions**: Granular permission system
- **Role Assignment**: Flexible role and permission assignment to users

### Default Roles
- **ADMIN**: Full system access including order configuration and user management
- **USER**: Customer access for ordering and profile management

## 💳 Payment Integration

Integrated with Razorpay for secure payment processing:
- Secure payment gateway integration
- Configurable maximum payment attempts (default: 2 attempts)
- Multiple payment attempts per order support
- Order confirmation emails
- Payment status tracking
- Refund capabilities

## 📧 Email Notifications

Automated email system for:
- Order confirmation
- Payment confirmation
- Order cancellation
- Payment failure notifications

## 🗃️ Database Features

- **MySQL** with Flyway migrations
- **CSV Data Import**: Geographic data loaded from CSV files
- **Batch Processing**: Optimized database operations (batch size: 100)
- **Optimized Queries**: Pagination and efficient data retrieval
- **Data Integrity**: Proper constraints and relationships
- **Configurable Flyway**: Control over migration, repair, and baseline operations

## ⚙️ Configuration Management

### Order Configuration
- **Tax Rate**: Configurable tax rate with system default
- **Discount**: Configurable discount values with system default
- **Payment Attempts**: Maximum retry attempts for failed payments

### File Upload
- **Max File Size**: 36MB per file
- **Max Request Size**: 36MB per request
- **Supported Formats**: Multiple image formats for food items

## 🔄 API Documentation

### User Management API

#### Base URL
```
http://localhost:8083/users
```

#### Authentication
- JWT Token-based authentication
- Include JWT token in Authorization header: `Authorization: Bearer <token>`
- Token obtained from login endpoint

## Base URL
```
http://localhost:8083/users
```

## Authentication
- JWT Token-based authentication
- Include JWT token in Authorization header: `Authorization: Bearer <token>`
- Token obtained from login endpoint

#### Endpoints

##### 1. User Registration
**POST** `/users/register`

Creates a new user account in the system.

#### Request Body
```json
{
  "firstName": "John",
  "middleName": "Michael",
  "lastName": "Doe",
  "dateOfBirth": "1990-05-15",
  "email": "john.doe@example.com",
  "countryCode": "+91",
  "phoneNumber": "1234567890",
  "password": "SecurePass123@",
  "confirmPassword": "SecurePass123@",
  "roleId": 2
}
```

#### Field Validations
| Field | Type | Required | Validation Rules |
|-------|------|----------|------------------|
| `firstName` | String | Yes | Only letters and spaces, cannot be blank |
| `middleName` | String | No | Only letters and spaces (optional) |
| `lastName` | String | No | Only letters and spaces (optional) |
| `dateOfBirth` | String | No | Format: YYYY-MM-DD (ISO format) |
| `email` | String | Yes | Valid email format |
| `countryCode` | String | Yes | Format: +1 to +9999 (1-4 digits after +) |
| `phoneNumber` | String | Yes | 10-digit number starting with 1-9 |
| `password` | String | Yes | Min 8 chars, 1 uppercase, 1 lowercase, 1 digit, 1 special char |
| `confirmPassword` | String | Yes | Must match password |
| `roleId` | Long | Yes | Valid role ID (2 for USER, 1 for ADMIN) |

#### Response
**Success (200 OK)**
```json
{
  "firstName": "John",
  "middleName": "Michael",
  "lastName": "Doe",
  "dateOfBirth": "1990-05-15",
  "countryCode": "+1",
  "phoneNumber": "9876543210",
  "email": "john.doe@example.com",
  "roleName": "USER"
}
```

**Validation Error (400 Bad Request)**
```json
{
  "firstName": "First name is required.",
  "email": "Invalid email format.",
  "password": "Password must be at least 8 characters long and contain one uppercase letter, one lowercase letter, one digit, and one special character."
}
```

---

##### 2. User Login
**POST** `/users/login`

Authenticates user credentials and returns JWT token.

#### Request Body
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123@"
}
```

#### Field Validations
| Field | Type | Required | Validation Rules |
|-------|------|----------|------------------|
| `email` | String | Yes | Valid email format |
| `password` | String | Yes | Not blank |

#### Response
**Success (200 OK)**
```json
{
  "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "status": "success",
  "message": "Login successful."
}
```

**Authentication Failed (401 Unauthorized)**
```json
{
  "status": "error",
  "message": "Invalid credentials"
}
```

---

##### 3. Update User
**PUT** `/users/{userId}`

Updates user information. Requires authentication.

#### Authorization
- **Roles Required:** `ADMIN` or `USER`
- **Access Control:** Users can update their own profile, Admins can update any user
- The user's UUID is automatically extracted from the request attribute, ensuring secure access
#### Path Parameters
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `userId` | Long | Yes | ID of the user to update |

#### Request Headers
```
Authorization: Bearer <jwt_token>
Content-Type: application/json
```

#### Request Body
```json
{
  "firstName": "John",
  "middleName": "Michael",
  "lastName": "Smith",
  "dateOfBirth": "1990-05-15",
  "email": "john.smith@example.com",
  "countryCode": "+91",
  "phoneNumber": "1234567890",
  "password": "NewSecurePass123@",
  "confirmPassword": "NewSecurePass123@",
  "roleId": 2
}
```

#### Response
**Success (200 OK)**
```json
{
  "firstName": "John",
  "middleName": "Michael",
  "lastName": "Smith",
  "dateOfBirth": "1990-05-15",
  "countryCode": "+191",
  "phoneNumber": "9876543210",
  "email": "john.smith@example.com",
  "roleName": "USER"
}
```

**Unauthorized (403 Forbidden)**
```json
{
  "status": "error",
  "message": "Access denied: You do not have permission to access this resource",
  "errorCode": "ACCESS_DENIED"
}
```

**User Not Found (404 Not Found)**
```json
{
  "status": "error",
  "message": "Invalid email or password.",
  "errorCode": "INTERNAL_SERVER_ERROR"
}
```

---

##### 4. Soft Delete User
**DELETE** `/users/{id}`

Performs soft delete on a user account (marks as deleted without removing from database).

#### Authorization
- **Roles Required:** `ADMIN` only
- **Access Control:** Only administrators can delete users

#### Path Parameters
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | Long | Yes | ID of the user to delete |

#### Request Headers
```
Authorization: Bearer <jwt_token>
```

#### Response
**Success (200 OK)**
```json
{
  "firstName": "John",
  "middleName": "Michael",
  "lastName": "Doe",
  "dateOfBirth": "1990-05-15",
  "countryCode": "+1",
  "phoneNumber": "9876543210",
  "email": "john.doe@example.com",
  "roleName": "USER"
}
```

**Unauthorized (403 Forbidden)**
```json
{
  "error": "Access denied. Admin role required."
}
```

**User Not Found (404 Not Found)**
```json
{
  "status": "error",
  "message": "User not found with id : ",
  "errorCode": "INTERNAL_SERVER_ERROR"
}
```

---

##### 5. Restore User
**PUT** `/users/restore/{id}`

Restores a soft-deleted user account.

#### Authorization
- **Roles Required:** `ADMIN` only
- **Access Control:** Only administrators can restore users

#### Path Parameters
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `id` | Long | Yes | ID of the user to restore |

#### Request Headers
```
Authorization: Bearer <jwt_token>
```

#### Response
**Success (200 OK)**
```json
{
  "firstName": "John",
  "middleName": "Michael",
  "lastName": "Doe",
  "dateOfBirth": "1990-05-15",
  "countryCode": "+1",
  "phoneNumber": "9876543210",
  "email": "john.doe@example.com",
  "roleName": "USER"
}
```

**Unauthorized (403 Forbidden)**
```json
{
  "status": "error",
  "message": "Access denied: You do not have permission to access this resource",
  "errorCode": "ACCESS_DENIED"
}
```

**User Not Found (404 Not Found)**
```json
{
  "status": "error",
  "message": "User not found with id: or is already active",
  "errorCode": "ACCESS_DENIED"
}
```

---

#### Common Error Responses

##### 400 Bad Request
Returned when request validation fails or required fields are missing.

##### 401 Unauthorized
Returned when JWT token is missing, invalid, or expired.

##### 403 Forbidden
Returned when user doesn't have required permissions for the requested operation.

##### 404 Not Found
Returned when requested user doesn't exist.

##### 500 Internal Server Error
Returned when an unexpected server error occurs.

---

#### Authentication Flow

1. **Register** a new user account using `/users/register`
2. **Login** with credentials using `/users/login` to receive JWT token
3. **Include** JWT token in `Authorization` header for protected endpoints
4. **Use** the token for subsequent API calls until it expires

---

#### Role-Based Access Control

##### Default Roles
- **ADMIN (roleId: 1)**: Full system access
   - Can create, update, delete, and restore any user
   - Can manage system configurations
   - Can access all admin endpoints

- **USER (roleId: 2)**: Customer access
   - Can update own profile
   - Can place orders and manage cart
   - Limited to customer-specific operations

##### Permission Matrix
| Endpoint | ADMIN | USER |
|----------|-------|------|
| POST /users/register | ✅ | ✅ |
| POST /users/login | ✅ | ✅ |
| PUT /users/{userId} | ✅ | ✅ (own profile) |
| DELETE /users/{id} | ✅ | ❌ |
| PUT /users/restore/{id} | ✅ | ❌ |

---

#### Security Notes

- All passwords are securely hashed before storage
- JWT tokens have configurable expiration times , with 10 hours expiry
- Rate limiting may be applied to prevent abuse
- Sensitive information is never returned in API responses

---

#### Example Usage

##### Register a new customer
```bash
curl -X POST http://localhost:8083/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane.smith@example.com",
    "countryCode": "+91",
    "phoneNumber": "9876543210",
    "password": "SecurePass123@",
    "confirmPassword": "SecurePass123@",
    "roleId": 2
  }'
```

##### Login and get JWT token
```bash
curl -X POST http://localhost:8083/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "jane.smith@example.com",
    "password": "SecurePass123@"
  }'
```

### Update user profile
```bash
curl -X PUT http://localhost:8083/users\
  -H "Authorization: Bearer <your_jwt_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Johnson",
    "email": "jane.johnson@example.com",
    "countryCode": "+91",
    "phoneNumber": "9876543210",
    "password": "NewSecurePass123@",
    "confirmPassword": "NewSecurePass123@",
    "roleId": 2
  }'
```
## 🚧 Upcoming Features

- **Unit Testing**: Comprehensive test coverage using JUnit5 and Mockito
- **Delivery System**: Future delivery management capabilities
- **OAuth2 Integration**: Social login capabilities
- **Bill Attachments**: Email bills with order confirmations
- **Enhanced Frontend**: Complete UI implementation
- **Advanced Analytics**: Order and sales analytics
- **Mobile API**: Optimized mobile endpoints

## 🧪 Testing

Comprehensive testing suite (upcoming):
- **JUnit5**: Unit testing framework
- **Mockito**: Mocking framework for isolated testing
- **Integration Tests**: End-to-end API testing
- **Test Coverage**: Comprehensive code coverage reports

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Spring Boot community for the excellent framework
- Razorpay for seamless payment integration
- AWS for reliable cloud storage
- MapStruct for efficient object mapping

## 📞 Contact

For any queries or support, please reach out:
- GitHub: [Aashish1226](https://github.com/Aashish1226)
- Email: aashishsaini1226@gmail.com
- LinkedIn: [Aashish Saini](https://www.linkedin.com/in/aashish-saini-133435245/)

⭐ **Star this repository if you find it helpful!**