# ISD.ICT.2023.2 Group 3

## Team member
| Name                   | StudentID   |Role        |
| :-----------------     | :---------- |:---------- |
| Ngô Xuân Bách          | 20215181    |Team Leader |
| Trần Thị Lan Anh       | 20215180    |Member      |
| Trần Thuỷ Châu         | 20215182    |Member      |
| Đào Minh Chí           | 20200082    |Member      |
| Bùi Hữu Thành Công     | 20205176    |Member      |

## Report Content
<details>
  <summary>From 20/05/2024 ~ to 26/05/2023 </summary>
<br>
<details>
<summary>Team Member 1: Ngô Xuân Bách</summary>
<br>

- Assigned tasks:
  - Create and set up database and connections

- Implementation details:
  - Pull Request(s): No PR (because creating init project)  https://github.com/ngobach26/ISD.ICT.20232.03/commit/c545587f52aa41bfb24fc05103b2a638189a8d5b?diff=split&w=0
  - Specific task details:
    - Create a temporary db(not final implementation )
    - Create initial project.

</details>

<details>
<summary>Team Member 2: Trần Thuỷ Châu</summary>
<br>

- Assigned tasks: Implement the home screen and product view

- Implementation details:
  - Pull Request(s): https://github.com/ngobach26/ISD.ICT.20232.03/commit/b8897760e9d0ed7d77c4e30df7343bfa3b3eb780
  - Specific task details:
    - Edit the HomeController, BaseController, Cart, Media, AimsException, MediaNotAvailableException according to the task
    - Create the home.fxml, media_home.fxml, popup.fxml and according handlers for those interfaces
</details>

<details>
<summary>Team Member 3: Trần Thị Lan Anh</summary>
<br>

- Assigned tasks: Implement the viewing cart

- Implementation details:
  - Pull Request(s): [#7](https://github.com/ngobach26/ISD.ICT.20232.03/pull/7)
  - Specific implementation details:
    - Implement controller, fxml view and handler for View Cart use case
</details>

<details>
<summary>Team Member 4: Đào Minh Chí</summary>
<br>

- Assigned tasks: Implement for VNPay interface and Pay order

- Implementation details:
  - Pull Request(s): 
  - Specific implementation details:
    - ...
    - ...
</details>

<details>
<summary>Team Member 5: Bùi Hữu Thành Công</summary>
<br>

- Assigned tasks: Implement Place Order, Place Rush Order function

- Implementation details:
  - Pull Request(s): https://github.com/ngobach26/ISD.ICT.20232.03/commit/24b4b415e10f6557b4b43a733da620a57e205688
  - Specific implementation details:
    - Implement Place Order and Place Rush Order functions
</details>
</details>

# Setup Instructions
## Working with IntelliJ

1. **Edit Run Configuration:**
  - Open your project in IntelliJ.
  - Navigate to `Run` > `Edit Configurations...`.

2. **Add VM Options:**
  - In the **VM options** field, add the following line:
    ```
    --module-path path\to\lib\your_os\javafx-sdk-21.0.1\lib --add-modules javafx.controls,javafx.fxml,javafx.web
    ```

3. **Example Configuration:**
  - If your JavaFX SDK is located at `D:\2023_2\ITSS\Check\TKXDPM.KHMT.20231-15-develop\lib\win\javafx-sdk-21.0.1\lib`, your VM options should look like this:
    ```
    --module-path D:\2023_2\ITSS\Check\TKXDPM.KHMT.20231-15-develop\lib\win\javafx-sdk-21.0.1\lib --add-modules javafx.controls,javafx.fxml,javafx.web
    ```

This configuration ensures that IntelliJ uses the correct JavaFX modules when running your application.


## Working with Eclipse

### Importing Project

1. **Import Repository:**
  - Clone the repository.
  - In Eclipse, go to `File` -> `Import...` -> `General` -> `Existing Projects into Workspace...`.
  - Select the root directory of the cloned repository.

### Setting Up SQLite

1. **Import sqlite-jdbc-3.7.2.jar:**
  - Place `sqlite-jdbc-3.7.2.jar` in the `lib` directory of your project.
  - In Eclipse, right-click on your project -> `Properties` -> `Java Build Path` -> `Libraries` -> `Add JARs...`.
  - Navigate to the `lib` directory and select `sqlite-jdbc-3.7.2.jar`.

### Setting Up JUnit

1. **Import JUnit5 Library:**
  - In Eclipse, right-click on your project -> `Properties` -> `Java Build Path`.
  - Under `Modulepath` or `Classpath`, depending on your project configuration:
    - `Add Library...` -> `JUnit` -> `JUnit 5`.

### Setting Up JavaFX

1. **Create User Library for JavaFX:**
  - In Eclipse, go to `Window` -> `Preferences` -> `Java` -> `Build Path` -> `User Libraries`.
  - Click `New` to create a new User Library (e.g., JavaFX15).
  - Add the JavaFX JARs from the directory specific to your operating system:
    - For Linux: `lib/linux/javafx-sdk-15/lib`
    - For Windows: `lib/win/javafx-sdk-15/lib`

2. **Include User Library in Project:**
  - Right-click on your project -> `Properties` -> `Java Build Path` -> `Libraries` -> `Add Library...`.
  - Select the User Library created (e.g., JavaFX15).

### Add VM Arguments

1. **Set VM Arguments for Running the Project:**
  - Click on `Run` -> `Run Configurations...` -> `Java Application`.
  - Create a new configuration for your project if not already created.
  - In the `VM arguments` section, add the appropriate JavaFX module path and modules:
    - For Linux:
      ```
      --module-path lib/linux/javafx-sdk-15/lib --add-modules=javafx.controls,javafx.fxml,javafx.base,javafx.web
      ```
    - For Windows:
      ```
      --module-path lib/win/javafx-sdk-15/lib --add-modules=javafx.controls,javafx.fxml,javafx.base,javafx.web
      ```

These steps ensure that Eclipse is properly configured to work with SQLite, JUnit, and JavaFX for your project.


