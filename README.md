## X2D - Xls to Database
    - This project attempts to be a simple java library that converts a whole Sheet to a JDBC Database
    - It will also work as a command line tool
    
### How to use
```
final X2D x2d = new X2D(X2DTest.class.getResourceAsStream("/sample_sheet.xls"));
// or
//final X2D x2d = new X2D("/home/myuser/sheet.xls");
final Connection conn = DriverManager.getConnection("jdbc:h2:test", "sa", "");
x2d.setConnection(conn);
// or
// x2d.setConnectionFromEnvironment(); //X2D_URL / X2D_USER / X2D_PASS
x2d.convert();
```

### How to use (command line tool)
```
# export X2D_URL=jdbc:h2:test
# export X2D_USER=sa
# export X2D_PASS=
# java -jar x2d.jar < myfile.xls
```
    
## Changelog
### 1.0a - await finishing to launch
     
    
#### TODO
    - fix date types interpred as double
    - Use streams (Prepare to huge sheets)