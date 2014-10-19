import java.util.*;
//Class that generates SpreadSheets .
// A spread book can contain any number of spreadsheet
public class SpreadBook {

    public static SpreadSheet makeSheet() throws Exception{
        List<String>input = new ArrayList<String>();
        List<Cell>reference = new ArrayList<Cell>();
        String [][] inter;
        int n = input.size();
        SpreadSheet sheet;
        // taking user input
        Scanner s = new Scanner(System.in);
        input.add(s.nextLine());
        // number of rows and columns
        int nCols = Integer.parseInt(input.get(0).split(" ")[0]);
        int nRows = Integer.parseInt(input.get(0).split(" ")[1]);
        //create a new sheet
        sheet = new SpreadSheet(nRows,nCols);
        int counter=0;
        // input from command line
        while(counter<sheet.getSize()){
            input.add(s.nextLine());
            counter++;
        }

        counter=1;
        //intermediate array for inserting elements to cell
        inter = new String[nRows][nCols];
        for(int i=-0;i<nRows;i++) {
            for (int j = 0;j < nCols; j++) {
                inter[i][j] = input.get(counter);
                //first updating numerical values and numerical equations
                if (isNumber(inter[i][j])) {
                    Cell c = sheet.getCell(i, j);

                    c.setValue(Integer.parseInt(inter[i][j]));
                    c.status = true;

                }
                else if (!isAlpha(inter[i][j])) {
                    //checks for numerical Equatoin
                    double result=0.0;
                    MathExpression m = new MathExpression(inter[i][j]);
                    result = m.result;
                    Cell c = sheet.getCell(i,j);
                    c.setValue(result);
                    c.status=true;
                }
                counter++;
            }
        }
        // traverse the matrix and update those values that are not already updated
        for(int i=0;i<nRows;i++){
            for(int j=0;j<nCols;j++){
                if(!sheet.getCell(i,j).status)
                {
                    insert(sheet,i,j,inter);
                }

            }
        }

        //insert(sheet,inter,0,0);
        return sheet;
    }
    // updating values for Expressions
    public static void insert(SpreadSheet sheet,int i,int j,String [][]matrix)throws CyclicDepandencyException{
        if(i*j > sheet.getSize())throw new CyclicDepandencyException();
        try{
            Cell c1 = sheet.getCell(i,j);
            String str =matrix[i][j];
            Cell c;
            String[] temp = str.split(" ");
            for(String t:temp) {
                if(!isNumber(t) && isAlpha(t)){

                    int row = (int) t.charAt(0) - (int) 'A';
                    int column = Integer.parseInt(t.charAt(1) + "") - 1;

                    c = sheet.getCell(row,column);

                    if(c.status) {
                        c1.addSubject(c);
                        //if increment operator :  update both cells current as well as the orignal
                        if(t.contains("++")){
                            c.setValue(c.getValue()+1);
                        }
                        if(t.contains("--")){
                            c.setValue(c.getValue()-1);
                        }
                    }
                    else{
                        //recursive call to set the subject of cureent as cell as it is still not set.
                        insert(sheet,row,column,matrix);
                        c1.addSubject(c);

                    }
                }

            }
            c1.setExpression(str);
            c1.setValue();
            c1.status=true;
        }
        catch (CyclicDepandencyException e){
            //in case of issues in the depandency
            e.printStackTrace();
        }
    }
    //check if letter
    public static boolean isAlpha(String s){
        for(int i=0;i<s.length();i++){
            if(Character.isLetter(s.charAt(i))) return true;
        }
        return false;

    }
    //test for number
    public static boolean isNumber(String s){

        int value = (int)s.charAt(0);
        if(value >= 48 && value <= 57 && !s.contains(" "))return true;
        else return false;
    }



    public static void main(String[] args)throws Exception{
        //creating sheet
        SpreadSheet sheet = makeSheet();
        System.out.println(sheet);

    }


}
