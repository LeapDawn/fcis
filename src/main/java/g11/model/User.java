package g11.model;

import lombok.ToString;

@ToString
public class User {
    private String id;

    private String password;

    private String name;

    private Byte gender;

    private String identityCard;

    private String tel;

    private String email;

    private Integer ownedAssociation;

    private Integer function;

    private String ownedAssociationName;

    public User() {
    }

    public User(String id, String password, String name, Byte gender, String identityCard, String tel, String email, Integer ownedAssociation, Integer function, String ownedAssociationName) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.identityCard = identityCard;
        this.tel = tel;
        this.email = email;
        this.ownedAssociation = ownedAssociation;
        this.function = function;
        this.ownedAssociationName = ownedAssociationName;
    }

    public String getOwnedAssociationName() {
        return ownedAssociationName;
    }

    public void setOwnedAssociationName(String ownedAssociationName) {
        this.ownedAssociationName = ownedAssociationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard == null ? null : identityCard.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getOwnedAssociation() {
        return ownedAssociation;
    }

    public void setOwnedAssociation(Integer ownedAssociation) {
        this.ownedAssociation = ownedAssociation;
    }

    public Integer getFunction() {
        return function;
    }

    public void setFunction(Integer function) {
        this.function = function;
    }
}