package com.example.prizebond;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Excel extends Activity implements OnClickListener{

    String path;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);

        View writeButton = findViewById(R.id.write);
        writeButton.setOnClickListener(this);
        View readButton = findViewById(R.id.read);
        readButton.setOnClickListener(this);
        View writeExcelButton = findViewById(R.id.writeExcel);
        writeExcelButton.setOnClickListener(this);
        View readExcelButton = findViewById(R.id.readExcel);
        readExcelButton.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.write:
                saveFile(this,"myFile.txt");
                break;
            case R.id.read:
                readFile(this,"myFile.txt");
                break;
            case R.id.writeExcel:
                saveExcelFile(this,"myExcel.xlsx");
                break;
            case R.id.readExcel:



                Intent myFileIntent= new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent,10);



                readExcelFile(this,"myExcel.xlsx");
                break;
        }
    }

    private static boolean saveFile(Context context, String fileName) {

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.w("FileUtils", "Storage not available or read only");
            return false;
        }

        // Create a path where we will place our List of objects on external storage
        File file = new File(context.getExternalFilesDir(null), fileName);
        PrintStream p = null; // declare a print stream object
        boolean success = false;

        try {
            OutputStream os = new FileOutputStream(file);
            // Connect print stream to the output stream
            p = new PrintStream(os);
            p.println(MainActivity.getInput);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != p)
                    p.close();
            } catch (Exception ex) {
            }
        }

        return success;
    }

    private static void readFile(Context context, String filename) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Log.w("FileUtils", "Storage not available or read only");
            return;
        }

        FileInputStream fis = null;

        try
        {
            File file = new File(context.getExternalFilesDir(null), filename);
            fis = new FileInputStream(file);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                Log.w("FileUtils", "File data: " + strLine);
                Toast.makeText(context, "File Data: " + strLine , Toast.LENGTH_SHORT).show();
            }
            in.close();
        }
        catch (Exception ex) {
            Log.e("FileUtils", "failed to load file", ex);
        }
        finally {
            try {if (null != fis) fis.close();} catch (IOException ex) {}
        }

        return;
    }

    private static boolean saveExcelFile(Context context, String fileName) {

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.w("FileUtils", "Storage not available or read only");
            return false;
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        //CellStyle cs;
       // cs = wb.createCellStyle();
       // cs.setFillForegroundColor(HSSFColor.LIME.index);
        //cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("my list");

        // Generate column headings
        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("MyNum");
      // c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("Winning");
      //  c.setCellStyle(cs);


//        int ri2=0;
//
//        //noinspection MismatchedReadAndWriteOfArray
//        String [] ad;
//        ad = new String[0];
//
//
//        String[] arrOfStr2 = MainActivity.getInputAdmin.split("\n");
//
//        for (String a : arrOfStr2) {
//            Log.d("TestCon", "onClick: " + a);
//
//
//            // Log.d("row", "saveExcelFile: "+c.getRow());
//
//            ad[ri2]=a;
//
//            ri2++;


        //}


        int ri=1;


        String[] arrOfStr = MainActivity.getInput.split("\n");

        for (String a : arrOfStr) {
            Log.d("TestCon", "onClick: " + a);


            Row row1 = sheet1.createRow(ri);

            c = row1.createCell(0);
            c.setCellValue(a);
            c = row1.createCell(1);
           // c.getNumericCellValue();
            c.setCellValue("ok");

            ri++;


        }











        // c.setCellStyle(cs);

        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));

        // Create a path where we will place our List of objects on external storage
        File file = new File(context.getExternalFilesDir(null), fileName);
        Log.d("getMSG", "saveExcelFile: ");

        try (FileOutputStream os = new FileOutputStream(file)) {
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        }

        return success;
    }

    private static void readExcelFile(Context context, String filename) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Log.w("FileUtils", "Storage not available or read only");
            return;
        }

        try{
            // Creating Input Stream
            File file = new File(context.getExternalFilesDir(null), filename);
            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /** We now need something to iterate through the cells.**/
            Iterator<Row> rowIter = mySheet.rowIterator();

            while(rowIter.hasNext()){
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator<Cell> cellIter = myRow.cellIterator();
                while(cellIter.hasNext()){
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    Log.w("FileUtils", "Cell Value: " +  myCell.toString());
                    Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){e.printStackTrace(); }
       // Log.w("FileUtils", "Storage not available or read only");

    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 10) {


                 path = data.getData().getPath();
           // String title = data.getData().get;

                Log.d("pathget", "onActivityResult: " + path);



        }



    }
}