package com.example.cuphead.view;

import java.util.List;
import java.util.Random;

public enum UserIcon {
    FEMALE1("icon/icons8-circled-user-female-skin-type-1-and-2-96.png","icon/icons8-circled-user-female-skin-type-1-and-2.gif"),
    FEMALE2("icon/icons8-circled-user-female-skin-type-3-96.png","icon/icons8-circled-user-female-skin-type-3.gif"),
    FEMALE3("icon/icons8-circled-user-female-skin-type-4-96.png","icon/icons8-circled-user-female-skin-type-4.gif"),
    FEMALE4("icon/icons8-circled-user-female-skin-type-6-96.png","icon/icons8-circled-user-female-skin-type-6.gif"),
    MALE1("icon/icons8-circled-user-male-skin-type-1-and-2-96.png","icon/icons8-circled-user-male-skin-type-1-and-2.gif"),
    MALE2("icon/icons8-circled-user-male-skin-type-3-96.png","icon/icons8-circled-user-male-skin-type-3.gif"),
    MALE3("icon/icons8-circled-user-male-skin-type-6-96.png","icon/icons8-circled-user-male-skin-type-6.gif"),
    OTHER("icon/icons8-cat-profile-96.png","icon/icons8-cat-profile-96.png");
    final String image;
    final String gif;
    private static final List<UserIcon> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    public static UserIcon randomIcon() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
    UserIcon(String image,String gif){
        this.image = image;
        this.gif = gif;
    }

}
