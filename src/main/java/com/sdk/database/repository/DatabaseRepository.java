package com.sdk.database.repository;

import com.sdk.data.types.Strings;
import com.sdk.database.connections.*;
import com.sdk.rs2xml.DbUtils;
import com.sdk.swingui.Swing;
import com.sdk.tools.OperatingSystem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DatabaseRepository implements TableListener, SqlListener {

    private Connection connection;

    public DatabaseRepository(MicrosoftAccessConnection microsoftAccessConnection) {
        connection = microsoftAccessConnection.getConnection();
    }

    public DatabaseRepository(SqliteConnection sqLiteConnection) {
        connection = sqLiteConnection.getConnection();
    }

    public DatabaseRepository(MysqlConnection mySQLConnection) {
        connection = mySQLConnection.getConnection();
    }

    public DatabaseRepository(SqlServerConnection sqlServerConnection) {
        connection = sqlServerConnection.getConnection();
    }

    public DatabaseRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean searchTable(String table, String column, String key) {
        try {
            String sql = getSelectSQL(table, new String[]{column}, column + " LIKE ?", "");
            Object data = null;

            try (PreparedStatement prstmt = connection.prepareStatement(sql)) {
                prstmt.setString(1, key);

                ResultSet rs = prstmt.executeQuery();
                while (rs.next()) {
                    data = rs.getString(column);
                }
            }

            return Objects.equals(data, key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeTable(String table) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE " + table);
            return true;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createTable(String table, Map<String, String> columns, String id) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ").append(table).append(" (");

        if (Objects.isNull(columns)) {
            return false;
        } else {
            if (columns.isEmpty()) {
                return false;
            }
        }
        
        if (tableExists(table)) {
            return true;
        }

        for (Map.Entry m : columns.entrySet()) {
            if (Objects.equals(m.getKey(), id)) {
                sql.append(m.getKey()).append(" ").append(m.getValue()).append(" PRIMARY KEY,");
            } else {
                sql.append(m.getKey()).append(" ").append(m.getValue()).append(",");
            }
        }

        char[] arr = sql.toString().toCharArray();
        new Strings().clearStringBuilder(sql);

        for (int i = 0; i < arr.length - 1; i++) {
            sql.append(arr[i]);
        }

        sql.append(");");

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql.toString());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean tableExists(String table) {
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, table, null);
            return tables.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean columnExists(String table, String column) {
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet columns = dbm.getColumns(null, null, table, column);
            return columns.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public long countRecords(String table) {
        try {
            int count = 0;
            ResultSet rs = connection.createStatement()
                    .executeQuery("SELECT COUNT(*) FROM " + table);

            while (rs.next()) {
                count = rs.getInt(1);
            }

            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public String getSelectSQL(String table, String[] columns, String condition, String orderby) {
        try {
            Strings strings = new Strings();

            if (strings.isNullOrEmpty(columns)) {
                return null;
            }

            StringBuilder sql = new StringBuilder("SELECT ");

            for (int i = 0; i < columns.length; i++) {
                sql.append(columns[i]);

                if ((i + 1) < columns.length) {
                    sql.append(",");
                }
            }

            sql.append(" FROM ").append(table);

            if (!strings.isNullOrEmpty(condition)) {
                sql.append(" WHERE ").append(condition);
            }

            if (!strings.isNullOrEmpty(orderby)) {
                sql.append(" ORDER BY ").append(orderby);
            }

            return sql.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getInsertSQL(String table, String[] values) {
        try {
            Strings strings = new Strings();
            StringBuilder sql = new StringBuilder("INSERT INTO ").append(table).append(" (");

            for (int i = 0; i < values.length; i++) {
                sql.append(values[i]);

                if ((i + 1) < values.length) {
                    sql.append(",");
                }
            }

            sql.append(") VALUES (");

            for (int i = 0; i < values.length; i++) {
                sql.append("?");

                if ((i + 1) < values.length) {
                    sql.append(",");
                }
            }

            return sql.append(")").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getInsertSQL(String table, Map<String, String> colValues) {
        try {
            Strings strings = new Strings();
            StringBuilder sql = new StringBuilder("INSERT INTO ").append(table).append(" (");
            int count = 0;

            if (Objects.isNull(colValues)) {
                return null;
            }

            for (Map.Entry<String, String> entry : colValues.entrySet()) {
                sql.append(entry.getKey());

                if ((count + 1) < colValues.size()) {
                    sql.append(",");
                }

                count++;
            }

            sql.append(") VALUES (");
            count = 0;

            for (Map.Entry<String, String> entry : colValues.entrySet()) {
                sql.append(entry.getValue());

                if ((count + 1) < colValues.size()) {
                    sql.append(",");
                }

                count++;
            }

            return sql.append(")").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getDeleteSQL(String table, String condition) {
        try {
            Strings strings = new Strings();

            StringBuilder sql = new StringBuilder("DELETE FROM ").append(table);

            if (strings.isNullOrEmpty(condition)) {
                return sql.toString();
            }

            return sql.append(" WHERE ").append(condition).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getUpdateSQL(String table, Map<String, String> colValues, String condition) {
        try {
            Strings strings = new Strings();
            StringBuilder sql = new StringBuilder("UPDATE ").append(table).append(" SET ");
            int count = 0;

            if (Objects.isNull(colValues)) {
                return null;
            }

            for (Map.Entry<String, String> entry : colValues.entrySet()) {
                sql.append(entry.getKey()).append(" = '").append(entry.getValue());

                count++;
                if ((count + 1) < colValues.size()) {
                    sql.append(",");
                }
            }

            if (!strings.isNullOrEmpty(condition)) {
                sql.append(" WHERE ").append(condition);
            }

            return sql.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Object> getColumnData(String table, String column, String type) {
        try {
            ArrayList<Object> values = new ArrayList<>();

            String sql = "SELECT " + column + " FROM " + table;
            try (PreparedStatement prstmt = connection.prepareStatement(sql)) {

                ResultSet rs = prstmt.executeQuery();
                while (rs.next()) {

                    switch (type.toLowerCase()) {
                        case "int":
                            values.add(rs.getInt(column));
                            break;

                        case "float":
                            values.add(rs.getFloat(column));
                            break;

                        case "bytes":
                            values.add(rs.getBytes(column));
                            break;

                        case "date":
                            values.add(rs.getDate(column));
                            break;

                        default:
                            values.add(rs.getString(column));
                            break;
                    }
                }
            }

            return values;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void loadJTable(String table, String[] columns, JTable jTable) {
        try {
            new Swing().clearTable(jTable, true);

            PreparedStatement st = connection.prepareStatement(getSelectSQL(table, columns, "", ""));
            ResultSet rs = st.executeQuery();
            jTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadJTable(String table, String[] columns, JTable jTable, int rows) {
        try {
            Swing swing = new Swing();
            swing.clearTable(jTable, true);

            PreparedStatement st = connection.prepareStatement(getSelectSQL(table, columns, "", ""));
            ResultSet rs = st.executeQuery();
            jTable.setModel(DbUtils.resultSetToTableModel(rs));

            DefaultTableModel model = (DefaultTableModel) jTable.getModel();
            if (rows == model.getRowCount()) {
                swing.clearTable(jTable, false);
            } else {
                if (rows > swing.countTableRows(jTable)) {
                    rows = model.getRowCount();
                }
            }

            for (int i = model.getRowCount() - 1; i >= rows; i--) {
                model.removeRow(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportJTableToExcel(JTable table, int cloumns, String path, boolean start) throws IOException {
        FileOutputStream fos = null;

        try {
            if (Objects.isNull(table)) {
                return;
            }

            if (new Strings().isNullOrEmpty(path)) {
                return;
            }

            int rowID = 0;
            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet ws = wb.createSheet();

            TreeMap<String, Object[]> data = new TreeMap<>();

            data.put("0", new Object[]{dtm.getColumnName(0), dtm.getColumnName(1),
                dtm.getColumnName(2), dtm.getColumnName(3), dtm.getColumnName(4), dtm.getColumnName(5)});

            for (int i = 0; i < dtm.getRowCount(); i++) {
                for (int j = 0; j < dtm.getColumnCount(); j++) {
                    data.put(String.valueOf(i + 1),
                            new Object[]{dtm.getValueAt(i, j).toString(), dtm.getValueAt(i, j++).toString(),
                                dtm.getValueAt(i, j++).toString(), dtm.getValueAt(i, j++).toString(),
                                dtm.getValueAt(i, j++).toString()});
                }
            }

            Set<String> ids = data.keySet();
            XSSFRow row;

            for (String key : ids) {
                row = ws.createRow(rowID++);
                Object[] values = data.get(key);

                int cellID = 0;
                for (Object o : values) {
                    Cell cell = row.createCell(cellID++);
                    cell.setCellValue(o.toString());
                }
            }

            if (!path.endsWith(".xlsx")) {
                path = path.concat(".xlsx");
            }

            fos = new FileOutputStream(path);
            wb.write(fos);
            
            if (start) {
                new OperatingSystem().openFile(path);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!Objects.isNull(fos)) {
                fos.close();
            }
        }
    }

}
