// Intial image data (0 = white, 1 = black)
var imageData = [ 0, 0, 0, 0, 1, 0, 0, 0, 0,
                  0, 0, 0, 1, 0, 1, 0, 0, 0,
                  0, 0, 1, 0, 0, 0, 1, 0, 0,
                  0, 1, 0, 0, 0, 0, 0, 1, 0,
                  0, 1, 0, 0, 0, 0, 0, 1, 0,
                  0, 1, 0, 0, 0, 0, 0, 1, 0,
                  0, 1, 0, 0, 0, 0, 0, 1, 0,
                  0, 1, 0, 0, 0, 0, 0, 1, 0,
                  0, 1, 1, 1, 1, 1, 1, 1, 0 ];

/**
 * Called on page load
 */
function onLoad()
{
  renderTableImage("imageDiv", 10, 9, 9, imageData);
}

/**
 * Rotates the image data, saves it, and re-renders it to the page
 * Called when the rotate button is pressed
 */
function rotateImage()
{
  var rotatedImage = new Array();

  // Read each column from the bottom up, starting at the bottom left corner
  for (var col = 0; col < 9; col++)
  {
    for (var row = 8; row >= 0; row--)
    {
      var cellIndex = (row * 9) + col;          // Since the image data is a linear array, we must do math to get the index
      rotatedImage.push(imageData[cellIndex]);  // Write each column as a row, effectively flipping the x an y axis
    }
  }

  // Save the transformed image data so that we can keep rotating it the next time this method is called
  imageData = rotatedImage;
  renderTableImage("imageDiv", 10, 9, 9, rotatedImage);
}

/**
 * Renders the image data as a table to the page
 */
function renderTableImage(divId, cellSize, rowCount, columnCount, cellData)
{
  var divElement = document.getElementById(divId);

  // Check to make sure all the input data is there and is sane
  // The cellData should be the product of the row and column count
  if (divElement && cellSize && cellData && cellData.length == rowCount * columnCount)
  {
    emptyElement(divElement);   // In case this is a re-render, remove the existing table from the div

    // Compute the table dimensions from input data and create the table
    var totalWidth = rowCount * cellSize;
    var totalHeight = columnCount * cellSize;
    var tableElement = divElement.appendChild(createTable("tableImage", totalWidth, totalHeight));

    // Iterate over image data and create TR and TD elements as necessary
    for (var row = 0; row < rowCount; row++)
    {
      var rowElement = tableElement.appendChild(createRow("row_" + row));
      for (var col = 0; col < columnCount; col++)
      {
        var cellId = "cell_" + row + "_" + col;
        var cellIndex = (row * columnCount) + col;  // Since the image data is a linear array, we must do math to get the index
        rowElement.appendChild(createCell(cellId, cellSize, cellData[cellIndex]));
      }
    }
  }
  else
  {
    console.log("Invalid input data");
  }
}

/**
 * Creates a TABLE element with the supplied id, width, and height
 */
function createTable(id, width, height)
{
  var tableElement = document.createElement("table");
  tableElement.id = id;
  tableElement.style.width = width;
  tableElement.style.height = height;
  return tableElement;
}

/**
 * Creates a TR element with the supplied id
 */
function createRow(id)
{
  var rowElement = document.createElement("tr");
  rowElement.id = id;
  return rowElement;
}

/**
 * Creates a table element with the supplied id, width, and height
 */
function createCell(id, cellSize, dataValue)
{
  var cellElement = document.createElement("td");
  cellElement.id = id;
  cellElement.style.width = cellSize;
  cellElement.style.height = cellSize;

  // Due to JavaScript's "truthiness" principle, 0 will be false (white) and 1 will be true (black)
  cellElement.style.backgroundColor = (dataValue) ? "black" : "white";
  
  return cellElement;
}

/**
 * Empties the target parent element of all children
 * Roughly equivalent to jQueries .empty() method
 */
function emptyElement(element)
{
  for (var i = 0; i < element.childNodes.length; i++)
  {
    element.removeChild(element.childNodes[i]);
  }
}