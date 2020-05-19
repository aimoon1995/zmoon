package com.project_study.my.ocr_frs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project_study.my.ocr_frs.items.IdCardItem;
import com.project_study.my.ocr_frs.items.VatInvoiceItem;
import com.project_study.my.ocr_frs.items.VatInvoiceListItem;
import com.project_study.my.ocr_frs.ocr.HWOcrClientToken;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OcrService
 * @Description: TODO
 * @Author zyl
 * @Date 2020/2/12
 * @Version V1.0
 **/
@Service
public class OcrService {


    public void tokenDemo(MultipartFile file) {
        /*
         * Token demo code
         * */
        String userName="xxx";
        String password="xxx";
        String domainName="xxx"; //If the current user is not an IAM user, the domainName is the same as the userName.
        String regionName="cn-north-4";
        String httpUri = "/v1.0/ocr/auto-classification";
        String imgPath = "./data/vat-invoice-demo.jpg";//File path or URL of the image to be recognized.

        // Set params except image
        JSONObject params = new JSONObject();
        List<String> typeList = new ArrayList<>();
        typeList.add("vat_invoice");
        typeList.add("id_card_portrait_side");
        params.put("type_list", typeList);
        try {
            HWOcrClientToken ocrClient= new HWOcrClientToken(domainName, userName, password, regionName);
            HttpResponse response=ocrClient.requestOcrServiceBase64(httpUri, imgPath, params);
            System.out.println(response);
            String content = IOUtils.toString(response.getEntity().getContent(), "utf-8");
            System.out.println(content);

            DecodeResponse(content);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void DecodeResponse(String response) {
        JSONObject responseObject = JSON.parseObject(response);
        System.out.println("****** responseObject = " + responseObject);
        String error_code = responseObject.getString("error_code");

        /*
         * data analysis
         * */
        if ("AIS.0000" == error_code || error_code == null || error_code == "") {
            JSONArray resultArr = responseObject.getJSONArray("result");

            for (int i = 0; i < resultArr.size(); i++) {
                JSONObject resultDict = resultArr.getJSONObject(i);
                JSONObject contentDict = resultDict.getJSONObject("content");
                String type = resultDict.getString("type");

                if ("id_card_portrait_side".equals(type)) {
                    IdCardItem idcardItem = new IdCardItem();
                    idcardItem.setName(contentDict.getString("name"));
                    idcardItem.setSex(contentDict.getString("sex"));
                    idcardItem.setEthnicity(contentDict.getString("ethnicity"));
                    idcardItem.setBirth(contentDict.getString("birth"));
                    idcardItem.setAddress(contentDict.getString("address"));
                    idcardItem.setNumber(contentDict.getString("number"));
                } else if ("vat_invoice".equals(type)) {
                    VatInvoiceItem vatInvoice = new VatInvoiceItem();
                    vatInvoice.setType(contentDict.getString("type"));
                    vatInvoice.setSeller_address(contentDict.getString("seller_address"));
                    vatInvoice.setAttribution(contentDict.getString("attribution"));
                    vatInvoice.setCode(contentDict.getString("code"));
                    vatInvoice.setCheck_code(contentDict.getString("check_code"));
                    vatInvoice.setMachine_number(contentDict.getString("machine_number"));
                    vatInvoice.setPrint_number(contentDict.getString("print_number"));
                    vatInvoice.setNumber(contentDict.getString("number"));
                    vatInvoice.setIssue_date(contentDict.getString("issue_date"));
                    vatInvoice.setEncryption_block(contentDict.getString("encryption_block"));
                    vatInvoice.setBuyer_name(contentDict.getString("buyer_name"));
                    vatInvoice.setBuyer_id(contentDict.getString("buyer_id"));
                    vatInvoice.setBuyer_address(contentDict.getString("buyer_address"));
                    vatInvoice.setBuyer_bank(contentDict.getString("buyer_bank"));
                    vatInvoice.setSeller_name(contentDict.getString("seller_name"));
                    vatInvoice.setSeller_id(contentDict.getString("seller_id"));
                    vatInvoice.setSeller_address(contentDict.getString("seller_address"));
                    vatInvoice.setSeller_bank(contentDict.getString("seller_bank"));
                    vatInvoice.setSubtotal_amount(contentDict.getString("subtotal_amount"));
                    vatInvoice.setSubtotal_tax(contentDict.getString("subtotal_tax"));
                    vatInvoice.setTotal(contentDict.getString("total"));
                    vatInvoice.setTotal_in_words(contentDict.getString("total_in_words"));
                    vatInvoice.setRemarks(contentDict.getString("remarks"));
                    vatInvoice.setReceiver(contentDict.getString("receiver"));
                    vatInvoice.setIssuer(contentDict.getString("issuer"));

                    JSONArray supervision_seal = contentDict.getJSONArray("supervision_seal");
                    for (Object object : supervision_seal) {
                        vatInvoice.getSupervision_seal().add(object.toString());
                    }

                    JSONArray seller_seal = contentDict.getJSONArray("seller_seal");
                    for (Object object : seller_seal) {
                        vatInvoice.getSeller_seal().add(object.toString());
                    }

                    JSONArray item_list = contentDict.getJSONArray("item_list");
                    for (Object object : item_list) {
                        JSONObject jsonItem = (JSONObject)object;
                        VatInvoiceListItem listItem = new VatInvoiceListItem();
                        listItem.setName(jsonItem.getString("name"));
                        listItem.setSpecification(jsonItem.getString("specification"));
                        listItem.setQuantity(jsonItem.getString("unit"));
                        listItem.setQuantity(jsonItem.getString("quantity"));
                        listItem.setUnit_price(jsonItem.getString("unit_price"));
                        listItem.setLicense_plate_number(jsonItem.getString("license_plate_number"));
                        listItem.setVehicle_type(jsonItem.getString("license_plate_number"));
                        listItem.setStart_date(jsonItem.getString("start_date"));
                        listItem.setEnd_date(jsonItem.getString("end_date"));
                        listItem.setAmount(jsonItem.getString("amount"));
                        listItem.setTax_rate(jsonItem.getString("tax_rate"));
                        listItem.setTax(jsonItem.getString("tax"));

                        vatInvoice.getItem_list().add(listItem);
                    }
                }
            }
        } else {
            System.out.println( "Failed to request the OCR API:" +responseObject);
        }
    }
}
