# Inventory Management System - Modern GUI Interface

## Overview
This application has been updated with a modern Point of Sale (POS) style dashboard interface that replaces the previous popup dialog system. The new interface provides a clean, professional look with improved usability and comprehensive inventory management features.

## Features

### Input Section
- Located at the top of the window
- Contains three labeled text fields:
  - **Item Name**: Enter the name of the item
  - **Price**: Enter the price of the item
  - **Quantity**: Enter the quantity of the item
- Dropdown to select item type (Computer Part or Car Part)
- Additional fields appear based on item type selection

### Action Button
- **Add to List**: Processes the entered data and adds the item to the inventory
- Also supports pressing Enter in any input field to trigger the action

### Data Display
- **JTable**: Displays all inventory items in a tabular format
- Columns include:
  - ID: Unique identifier for each item
  - Name: The name of the item
  - Price: The price of the item
  - Quantity: The quantity in stock
  - Total: Calculated total value (Price × Quantity)
  - Type: The category of the item
  - Details: Additional information based on item type

### Operation Buttons
Located below the inventory table:
- **Delete Selected**: Removes the selected item from inventory
- **Update Quantity**: Modifies the quantity of the selected item
- **Search Item**: Finds items by ID or name

### Additional Controls
- **Refresh**: Updates the table to show the latest inventory
- **Save Data**: Saves the current inventory to the file system
- Status bar showing current operation status

## Visual Design
- Clean, professional layout using BorderLayout and GridBagLayout
- Scrollable table for easy navigation through large inventories
- Proper padding and spacing for visual appeal
- Color-coded buttons for better visual hierarchy
- Tooltips for guidance on form fields

## Usage
1. Fill in the item details in the input section
2. Select the appropriate item type from the dropdown
3. Fill in the additional fields that appear based on item type
4. Click "Add to List" or press Enter to add the item to inventory
5. View all items in the table below
6. Use the operation buttons below the table to manage inventory:
   - Select an item and click "Delete Selected" to remove it
   - Select an item and click "Update Quantity" to change its quantity
   - Click "Search Item" to find specific items
7. Use the "Save Data" button to persist changes to disk
8. Use the "Refresh" button to update the display

## Technical Implementation
- Uses Java Swing for the GUI components
- Integrates seamlessly with existing InventoryManager and FileManager
- Maintains all original functionality while improving the user experience
- Follows object-oriented design principles with proper encapsulation
- Implements comprehensive CRUD operations (Create, Read, Update, Delete)