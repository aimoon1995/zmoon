//package com.project_study.quartz.common.utils;
//
//import com.google.zxing.*;
//import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.common.HybridBinarizer;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.util.Hashtable;
//
///**
// * 功能说明:二维码编码、解码 2012-2-2 下午04:12:06 Yang 创建文件 修改说明: 创建文件 2012-2-2 下午04:12:06 Yang 修改文件
// * @author YangDong
// */
//public class CodeBarUtil {
//    /**
//     * 黑色
//     */
//    public static final int BLACK = 0xff000000;
//
//    /**
//     * 白色
//     */
//    public static final int WHITE = 0xFFFFFFFF;
//
//    /**
//     * utf8
//     */
//    public static final String UTF8 = "utf-8";
//
//    /**
//     * png
//     */
//    public static final String PNG = "png";
//
//    /**
//     * .
//     */
//    public static final String POINT = ".";
//
//    /**
//     * 100
//     */
//    public static final int HUNDRED = 100;
//
//    /**
//     * 功能 :仅仅指定要生成二维码的字符串 开发：Yang 2012-2-2 下午05:13:02
//     *
//     * @param content
//     *            要编码的字符串
//     * @return 条码
//     * @throws WriterException
//     * @throws UnsupportedEncodingException
//     */
//    public static byte[] encode4Shp(String content) throws WriterException, UnsupportedEncodingException {
//        return encode2Code128(content, 63, 311);
//    }
//
//    /**
//     * 功能 :仅仅指定要生成条形码的字符串 开发：Yang 2012-2-2 下午05:13:02
//     *
//     * @param content
//     *            要编码的字符串
//     * @return 条码
//     * @throws WriterException
//     * @throws UnsupportedEncodingException
//     */
//    public static byte[] encode2Code128(String content, int imgHeigth, int imgWidth) throws WriterException, UnsupportedEncodingException {
//        byte[] byteContent = content.getBytes(UTF8);
//        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//        hints.put(EncodeHintType.CHARACTER_SET, UTF8);
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(new String(byteContent, UTF8), BarcodeFormat.CODE_128, imgWidth, imgHeigth, hints);
//        int width = bitMatrix.getWidth();
//        int height = bitMatrix.getHeight();
//
//        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//
//        Graphics g = image.getGraphics();
//        // 绘制空白
//        for (int x = 0; x < width; ++x) {
//            for (int y = height - 22; y < height; ++y) {
//                image.setRGB(x, y, Color.WHITE.getRGB());
//            }
//        }
//        g.setColor(new Color(102, 102, 102));
//        g.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 13));
//        int contentSize = content.length();
//        int stringWidth = width / (contentSize + 5);
//        for (int i = 0; i < contentSize; i++) {
//            g.drawString(String.valueOf(content.charAt(i)), stringWidth * (i + 3), height - 10);
//        }
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        image.flush();
//        byte[] bytes = null;
//        try {
//            ImageIO.write(image, PNG, out);
//            bytes = out.toByteArray();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        } finally {
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return bytes;
//    }
//
//    /**
//     * 生成一维码格式Code128
//     *
//     * @param content
//     *            文本内容
//     * @param width
//     *            宽度
//     * @param height
//     *            高度
//     * @return BitMatrix 矩阵
//     * @throws WriterException
//     */
//    public static BitMatrix encodeCode128(String content, int width, int height) throws WriterException {
//        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//        hints.put(EncodeHintType.CHARACTER_SET, UTF8);
//        BitMatrix bitMatrix = null;
//        bitMatrix = encode(content, BarcodeFormat.CODE_128, width, height, hints);
//        return bitMatrix;
//    }
//
//    /**
//     * 生成二维码格式QRCode
//     *
//     * @param content
//     *            文本内容
//     * @param width
//     *            宽度
//     * @param height
//     *            高度
//     * @return BitMatrix 矩阵
//     * @throws WriterException
//     */
//    public static BitMatrix encodeQRCode(String content, int width, int height) throws WriterException {
//        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//        hints.put(EncodeHintType.CHARACTER_SET, UTF8);
//        BitMatrix bitMatrix = null;
//        bitMatrix = encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//        return bitMatrix;
//    }
//
//    /**
//     * 功能 :指定要生成二维码的字符串和保存的图片名称 开发：Yang 2012-2-2 下午05:13:02
//     *
//     * @param content
//     *            要编码的字符串
//     * @return byteOut
//     * @throws WriterException
//     * @throws IOException
//     */
//    public static ByteArrayOutputStream encodeQRCode(String content) throws WriterException, IOException {
//        BitMatrix bitMatrix = encodeQRCode(content, HUNDRED, HUNDRED);
//        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//        BufferedImage bi = toBufferedImage(bitMatrix);
//        bi.flush();
//        ImageIO.write(bi, PNG, byteOut);
//        return byteOut;
//    }
//
//    public static void encodeQRCode(String content, int width, int height, String descFilePath) throws Exception{
//    	 BitMatrix bitMatrix = encodeQRCode(content, width, height);
//         BufferedImage bi = toBufferedImage(bitMatrix);
//         bi.flush();
//         File file = new File(descFilePath);
//         if(!file.exists()){
//        	 file.createNewFile();
//         }
//         ImageIO.write(bi, PNG, file);
//    }
//
//    /**
//     * 将矩阵写入到输出流中
//     *
//     * @param matrix
//     * @param format
//     * @param stream
//     * @throws IOException
//     */
//    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
//        BufferedImage image = toBufferedImage(matrix);
//        if (!ImageIO.write(image, format, stream)) {
//            throw new IOException("Could not write an image of format " + format);
//        }
//    }
//
//    /**
//     * 使用ZXING 生成条码或者二维码
//     *
//     * @param content
//     *            内容字符串
//     * @param format
//     *            格式 条码 BarcodeFormat.CODE_128/ BarcodeFormat.QR_CODE
//     * @param width
//     *            宽度
//     * @param height
//     *            高度
//     * @param hints
//     *            参数
//     * @return BitMatrix 矩阵
//     * @throws WriterException
//     */
//    public static BitMatrix encode(String content, BarcodeFormat format, int width, int height, Hashtable<EncodeHintType, String> hints)
//            throws WriterException {
//        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
//        BitMatrix matrix;
//        if (!hints.isEmpty()) {
//            matrix = barcodeWriter.encode(content, format, width, height, hints);
//        } else {
//            matrix = barcodeWriter.encode(content, format, width, height);
//        }
//        return matrix;
//    }
//
//    /**
//     * 功能 : 开发：Yang 2012-2-2 下午04:24:20
//     *
//     * @param path
//     *            保存图片文件的路径
//     * @param fileName
//     *            保存图片文件的名称
//     * @param content
//     *            要编码的内容
//     * @param suffix
//     *            生成图片的扩展名
//     * @param coding
//     *            编码格式
//     * @param imgWidth
//     *            宽度
//     * @param imgHeight
//     *            高度
//     * @param bf
//     *            编码类型
//     * @throws Exception
//     *             异常
//     */
//    public static void encode(String path, String fileName, String content, String suffix, String coding, BarcodeFormat bf, int imgWidth,
//            int imgHeight) throws Exception {
//        byte[] byteContent = content.getBytes(coding);
//        BitMatrix bitMatrix;
//        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//        hints.put(EncodeHintType.CHARACTER_SET, coding);
//        bitMatrix = new MultiFormatWriter().encode(new String(byteContent, coding), bf, imgWidth, imgHeight, hints);
//        File file = new File(path + fileName + POINT + suffix);
//        BufferedImage bi = toBufferedImage(bitMatrix);
//        bi.flush();
//        file.mkdirs();
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        ImageIO.write(bi, suffix, file);
//    }
//
//    /**
//     * 功能 :生成缓冲图片文件 开发
//     *
//     * @param matrix
//     *            BitArray
//     * @return BufferedImage
//     */
//    public static BufferedImage toBufferedImage(BitMatrix matrix) {
//        int width = matrix.getWidth();
//        int height = matrix.getHeight();
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
//            }
//        }
//        return image;
//    }
//
//    /**
//     * 功能 :按文件路径解码 开发：Yang 2012-2-2 下午04:58:27
//     *
//     * @param path
//     *            要解码的图片路径(路径+名称+扩展名)
//     * @return 解码结果字符串
//     * @throws Exception
//     *             异常
//     */
//    public static String decode(String path) throws Exception {
//        String resultStr = "";
//        File file = new File(path);
//        BufferedImage image = null;
//        image = ImageIO.read(file);
//        LuminanceSource source = new BufferedImageLuminanceSource(image);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//        Result result;
//        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
//        hints.put(DecodeHintType.CHARACTER_SET, UTF8);
//        result = new MultiFormatReader().decode(bitmap, hints);
//        resultStr = result.getText();
//        return resultStr;
//    }
//
//    /**
//     * 功能 :文件输入流解码 开发：Yang 2012-2-2 下午04:58:27
//     *
//     * @param inputStream
//     *            文件输入流
//     * @return 解码结果字符串
//     * @throws Exception
//     *             异常
//     */
//    public static String decode(InputStream inputStream) throws Exception {
//        String resultStr = "";
//        BufferedImage image = null;
//        image = ImageIO.read(inputStream);
//        LuminanceSource source = new BufferedImageLuminanceSource(image);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//        Result result;
//        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
//        hints.put(DecodeHintType.CHARACTER_SET, UTF8);
//        result = new MultiFormatReader().decode(bitmap, hints);
//        resultStr = result.getText();
//        return resultStr;
//    }
//
//	/**
//	 * 去掉二维码的白边
//	 * @param matrix
//	 * @return
//	 */
//	public static BitMatrix deleteWhite(BitMatrix matrix){
//	    int[] rec = matrix.getEnclosingRectangle();
//	    int resWidth = rec[2] + 1;
//	    int resHeight = rec[3] + 1;
//
//	    BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
//	    resMatrix.clear();
//	    for (int i = 0; i < resWidth; i++) {
//	        for (int j = 0; j < resHeight; j++) {
//	            if (matrix.get(i + rec[0], j + rec[1])) {
//	                resMatrix.set(i, j);
//	            }
//	        }
//	    }
//	    return resMatrix;
//	}
//}
