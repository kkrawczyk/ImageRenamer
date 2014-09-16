/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.novait.imagerenamer.model;

import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Krzysztof
 */
public class ImageFileComparator implements Comparator<ImageFile> {

    @Override
    public int compare(ImageFile if1, ImageFile if2) {
        Date d1 = this.getDateFromImageFile(if1);
        Date d2 = this.getDateFromImageFile(if2);
        if (d1.compareTo(d2) > 0) {
            return 1;
        } else if (d1.compareTo(d2) < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    private Date getDateFromImageFile(ImageFile imageFile) {
        //System.out.print("Plik: " + imageFile.getFilepath().getAbsolutePath());
        Date date = imageFile.getExifDate();
        String dateSource = "EXIF";
        if (date == null) {
            date = imageFile.getFileCreationDate();
            dateSource = "FILE";
        }
        //System.out.print(" źródło daty: " + dateSource + " - " + date);
        //System.out.println();
        return date;
    }

}
