# 🌟 ASH-SHAHEED 🌟

**ASH-SHAHEED** is a comprehensive tool designed to manage and display martyr data efficiently. 

## 📝 Note on Project Differences

The only difference between **Project 1** and **Project 3** is the data structure used

## 🔧 Data Structures

In this project, you will implement a martyrs data structure using a combination of data structures to manage and efficiently access martyr data. The overall data structure includes:

- **Sorted Doubly Circular Linked List:** 
  - Contains location records sorted by location.
  - Each location record includes two AVL trees:
    - **1st AVL Tree:** Stores Martyr records sorted by Martyr’s name.
    - **2nd AVL Tree:** Stores `DateStack` objects sorted by date.
      - **DateStack Object:** Contains a date and a Stack of martyrs' pointers.

- **AVL Trees:**
  - **Martyr Records AVL Tree:** Manages martyr records sorted by name.
  - **DateStack Objects AVL Tree:** Manages `DateStack` objects, each containing a date and a stack of pointers to martyrs.

- **Stacks and Queues:** Used for managing and accessing data efficiently within the DateStack objects and other parts of the system.

## 💻 Main Interface

The main interface provides a simple option to upload martyr data for processing.

![Main Interface](FirstProject_Martyrs/src/Resources/1.jpg)

## 🚨 Data Upload Notification

The notification indicates successful data upload, though some records might be ignored due to incomplete data.

![Upload Notification](FirstProject_Martyrs/src/Resources/2.jpg)

## 🌍 Location Information Management

Users can:

- Insert new location information
- Update or delete existing locations
- Search for locations

![Location Management](FirstProject_Martyrs/src/Resources/3.jpg)
![Location Management](FirstProject_Martyrs/src/Resources/4.jpg)

## 📋 Martyr Information Management

Users can:

- Insert new martyr information
- Update or delete existing records
- Search by location

![Martyr Management](FirstProject_Martyrs/src/Resources/5.jpg)

## 📊 Martyr Statistics

View summary statistics for selected locations, including:

- Martyr counts by age and gender
- Average age of martyrs
- Date with the maximum number of martyrs
- Percentage of martyrs in Gaza compared to the total

![Martyr Statistics](FirstProject_Martyrs/src/Resources/6.jpg)

## 💾 Save Martyr File

Users can save martyr information to a file for future reference.
