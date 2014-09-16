/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.novait.imagerenamer.model;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Krzysztof
 */
public class ImageFile {

    protected File filepath;
    protected String filename;
    protected Date fileCreationDate;
    protected Date exifDate;
    protected String thumbnailFilename;
    protected BufferedImage thumbnail;
    private String extension;
    private String proposedFilename;

    public ImageFile(String filepath) {
        this.filepath = new File(filepath);
        this.filename = this.filepath.getName();
        this.proposedFilename = "";
        this.extension = FilenameUtils.getExtension(this.filepath.getName());
        try {
            BasicFileAttributes attr = Files.readAttributes(this.filepath.toPath(), BasicFileAttributes.class);
            this.fileCreationDate = new Date(attr.creationTime().toMillis());
        } catch (IOException ex) {
            Logger.getLogger(ImageFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        createThumbnail();
    }

    private void createThumbnail() {
        try {
            BufferedImage tmp = ImageIO.read(this.filepath);
            int tmpW = tmp.getWidth();
            int tmpH = tmp.getHeight();
            double ratio = (double) tmpW / (double) tmpH;
            int w = 400;
            int h = (int) Math.round(w / ratio);
            BufferedImage bi = getCompatibleImage(w, h);
            Graphics2D g2d = bi.createGraphics();
            double xScale = (double) w / tmp.getWidth();
            double yScale = (double) h / tmp.getHeight();
            AffineTransform at = AffineTransform.getScaleInstance(xScale, yScale);
            g2d.drawRenderedImage(tmp, at);
            g2d.dispose();
            this.setThumbnail(bi);
        } catch (IOException ex) {
            Logger.getLogger(ImageFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private BufferedImage getCompatibleImage(int w, int h) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage image = gc.createCompatibleImage(w, h);
        return image;
    }

    private void readExifDate() {
        if (this.filepath != null && this.filepath.getAbsolutePath().toLowerCase().endsWith("jpg")) {
            try {
                Metadata metadata = ImageMetadataReader.readMetadata(this.filepath);
                Directory directory = metadata.getDirectory(ExifDirectory.class);
                if (directory != null) {
                    this.exifDate = directory.getDate(ExifDirectory.TAG_DATETIME);
                }
            } catch (ImageProcessingException | MetadataException | NullPointerException ex) {
                Logger.getLogger(ImageFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ImageFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the fileCreationDate
     */
    public Date getFileCreationDate() {
        return fileCreationDate;
    }

    /**
     * @param fileCreationDate the fileCreationDate to set
     */
    public void setFileCreationDate(Date fileCreationDate) {
        this.fileCreationDate = fileCreationDate;
    }

    /**
     * @return the exifDate
     */
    public Date getExifDate() {
        if (exifDate == null) {
            readExifDate();
        }
        return exifDate;
    }

    /**
     * @param exifDate the exifDate to set
     */
    public void setExifDate(Date exifDate) {
        this.exifDate = exifDate;
    }

    /**
     * @return the thumbnailFilename
     */
    public String getThumbnailFilename() {
        return thumbnailFilename;
    }

    /**
     * @param thumbnailFilename the thumbnailFilename to set
     */
    public void setThumbnailFilename(String thumbnailFilename) {
        this.thumbnailFilename = thumbnailFilename;
    }

    /**
     * @return the filepath
     */
    public File getFilepath() {
        return filepath;
    }

    /**
     * @param filepath the filepath to set
     */
    public void setFilepath(File filepath) {
        this.filepath = filepath;
    }

    /**
     * @return the thumbnail
     */
    public BufferedImage getThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail the thumbnail to set
     */
    public void setThumbnail(BufferedImage thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return the proposedFilename
     */
    public String getProposedFilename() {
        return proposedFilename;
    }

    /**
     * @param proposedFilename the proposedFilename to set
     */
    public void setProposedFilename(String proposedFilename) {
        this.proposedFilename = proposedFilename;
    }

}
