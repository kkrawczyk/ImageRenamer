/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.novait.imagerenamer.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Krzysztof
 */
public class ImageTableModel extends AbstractTableModel {

    private List<ImageFile> list;

    public final static int COLUMN_THUMBNAIL = 0;
    public final static int COLUMN_FILENAME = 1;
    public final static int COLUMN_PROPOSED_FILENAME = 2;
    public final static int COLUMN_FILEDATE = 3;
    public final static int COLUMN_EXIFDATE = 4;

    public ImageTableModel() {
        this.list = new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case ImageTableModel.COLUMN_THUMBNAIL:
                return "Podgląd";
            case ImageTableModel.COLUMN_FILENAME:
                return "Nazwa pliku";
            case ImageTableModel.COLUMN_PROPOSED_FILENAME:
                return "Proponowana nazwa pliku";
            case ImageTableModel.COLUMN_FILEDATE:
                return "Data z pliku";
            case ImageTableModel.COLUMN_EXIFDATE:
                return "Data z danych EXIF";
            default:
                return "";
        }
    }

    @Override
    public int getRowCount() {
        return this.list.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ImageFile imageFile = this.list.get(rowIndex);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        switch (columnIndex) {
            case ImageTableModel.COLUMN_THUMBNAIL:
                if (imageFile.getThumbnail() != null) {
                    return imageFile.getThumbnail();
                } else {
                    return "";
                }
            case ImageTableModel.COLUMN_FILENAME:
                return imageFile.getFilename();
            case ImageTableModel.COLUMN_FILEDATE:
                if (imageFile.getFileCreationDate() != null) {
                    return df.format(imageFile.getFileCreationDate());
                } else {
                    return "";
                }
            case ImageTableModel.COLUMN_EXIFDATE:
                if (imageFile != null && imageFile.getExifDate() != null) {
                    return df.format(imageFile.getExifDate());
                } else {
                    return "";
                }
            default:
                return "";
        }
    }

    public void addImageFile(ImageFile imageFile) {
        this.list.add(imageFile);
        this.fireTableDataChanged();
    }

    public void removeImageFile(ImageFile imageFile) {
        this.list.remove(imageFile);
        this.fireTableDataChanged();
    }

}
