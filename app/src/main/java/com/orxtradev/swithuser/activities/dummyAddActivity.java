package com.orxtradev.swithuser.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.orxtradev.swithuser.model.ContactUs;
import com.orxtradev.swithuser.model.CoponModel;
import com.orxtradev.swithuser.model.MandoubModel;
import com.orxtradev.swithuser.model.Offers;
import com.orxtradev.swithuser.model.PreviewMModel;
import com.orxtradev.swithuser.model.PreviewOrder;
import com.orxtradev.swithuser.model.User;
import com.orxtradev.swithuser.model.equiment;
import com.orxtradev.swithuser.model.equimentPieces;
import com.orxtradev.swithuser.model.maintenanceOrder;

import java.util.Random;

public class dummyAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void add() {

        addUser();
//        addِEquipment();
//        addOffer();
//        addPreviewOrder();
//        addEquimentPieces();
//        addCopon();
//        addmaintanceOrder();
//        addContactUs();
//        addPreview();

        addMandoub();

    }

    private void addMandoub() {

        MandoubModel model = new MandoubModel();

        model.setId(random());
        model.setName("احمد ابراهيم");
        model.setCode("mm3124");
        model.setPassword("21341");
        model.setPhone("010132321322");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Mandoub")
                .child(model.getId()).setValue(model);

    }

    private void addPreview() {


        PreviewMModel model = new PreviewMModel();
        model.setId(random());
        model.setPrice("100");
        model.setTime(System.currentTimeMillis() + "");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("MaintenancePrice")
                .child(model.getId()).setValue(model);
    }

    private void addContactUs() {

        ContactUs model = new ContactUs();

        model.setId(random());
        model.setUserId("QSJARIIWE1YI26V04Z");
        model.setGovernment("cairo");
        model.setMessage("عندي مشكلة مع المندووب معملش الجهاز");
        model.setName("محمد احمد");
        model.setPhone("01023321923");
        model.setTime(System.currentTimeMillis() + "");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("ContactUs")
                .child(model.getId()).setValue(model);
    }

    private void addCopon() {

        CoponModel model = new CoponModel();
        model.setId(random());
        model.setCode("AS123");
        model.setName("خصومات رمضان");
        model.setStatus("1");
        model.setTime(System.currentTimeMillis() + "");
        model.setValue("20");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("CoponModel")
                .child(model.getId()).setValue(model);
    }

    private void addmaintanceOrder() {
        maintenanceOrder model = new maintenanceOrder();
        model.setId(random());
        model.setTime(System.currentTimeMillis() + "");
        model.setName("ابراهيم محمود محمد ");
        model.setAddress("٥ شارع احمد حمدي");
        model.setBuildingNumber("5");
        model.setFlatNumber("11");
        model.setEquimentId("UFCL8IF3TX9ZTMPUQI");
        model.setGovernment("october");
        model.setLandmark("امام كليه الطب");
        model.setLang("");
        model.setLat("");
        model.setPhone("01014722323");
        model.setPhone2("");
        model.setUserId("QSJARIIWE1YI26V04Z");
        model.setFromClock("10:00");
        model.setToClock("17:00");
        model.setNote("الجهاز فيه عطل مش بيطلع ماية");
        model.setPieceNumber("2");
        model.setPieceId("FE0N6U2TRD2KKU4J7U");
        model.setDay("السبت");
        model.setTotalPrice("100");
        model.setCoponId("HXSGV1OIHNC4Q9LVIP");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("MaintenanceOrders")
                .child(model.getId()).setValue(model);
    }

    private void addEquimentPieces() {

        equimentPieces model = new equimentPieces();
        model.setId(random());
        model.setTime(System.currentTimeMillis() + "");
        model.setName("شمعة ٣ شهور");
        model.setEquimentId("UFCL8IF3TX9ZTMPUQI");
        model.setImage("");
        model.setPrice("20");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("ِEquipmentPieces")
                .child(model.getId()).setValue(model);
    }


    private void addPreviewOrder() {

        PreviewOrder model = new PreviewOrder();
        model.setId(random());
        model.setTime(System.currentTimeMillis() + "");
        model.setName("ابراهيم محمود محمد ");
        model.setAddress("٥ شارع احمد حمدي");
        model.setBuildingNumber("5");
        model.setFlatNumber("11");
        model.setEquimentTypeId("UFCL8IF3TX9ZTMPUQI");
        model.setGovernment("october");
        model.setLandmark("امام كليه الطب");
        model.setLang("");
        model.setLat("");
        model.setPhone("01014722323");
        model.setPhone2("");
        model.setUserId("QSJARIIWE1YI26V04Z");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("PreviewOrders")
                .child(model.getId()).setValue(model);
    }

    private void addOffer() {

        Offers model = new Offers();
        model.setId(random());
        model.setName("عروض الصيف");
        model.setDesc("خصم 6 %");
        model.setTime(System.currentTimeMillis() + "");
        model.setStatus("0");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Offers")
                .child(model.getId()).setValue(model);
    }

    private void addِEquipment() {
        equiment model = new equiment();


        model.setId(random());
        model.setName("فلتر JO");
        model.setDesc("3 شمعة");
        model.setPrice("100");
        model.setTime(System.currentTimeMillis() + "");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("ِEquipment")
                .child(model.getId()).setValue(model);

    }

    private void addUser() {
        User user = new User();
        user.setId(random());
        user.setName("mohamed ahmed");
        user.setGovernment("cairo");
        user.setCity("october");
        user.setGender("male");
        user.setPhone("01014722323");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Users")
                .child(user.getId()).setValue(user);
    }


    /**
     * to get ids for the firebase
     *
     * @return random string
     */
    protected String random() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

}
