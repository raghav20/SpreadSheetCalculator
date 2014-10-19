public class SpreadSheet{

  private int nRows;
  private int nCols;
  private Cell[][] cells;

  public SpreadSheet(int nRows, int nCols) {
		this.nRows = nRows;
		this.nCols = nCols;
		cells = new Cell[nRows][nCols];
		for(int i = 0; i < nRows; i++) {
			for(int j = 0; j < nCols; j++) {
				cells[i][j] = new Cell();
			}
		}
	}

  //default spreadsheet created if nothing is passed in parameter
  public SpreadSheet(){
    this(5,5);
  }

  public Cell getCell(int row, int col) {
		if (0 <= row && row < nRows && 0 <= col && col < nCols) {
			return cells[row][col];
		}
		return null;
  }


  public int getSize(){
      return this.nRows*this.nCols;
  }
  public String toString() {
		String result = "";
		for(int i = 0; i < nRows; i++) {
			for(int j = 0; j < nCols; j++) {
				result = result + cells[i][j].getValue() + '\n';
			}
		}
        //result = String.format("%.5f"+result);
		return result;
	}

}
