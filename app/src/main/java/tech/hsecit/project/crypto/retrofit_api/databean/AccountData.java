package tech.hsecit.project.crypto.retrofit_api.databean;


public class AccountData
{
    public String bank_account_number;

    public String address;

    public Wallet wallet;

    public String phone;

    public String nom;

    public String getBank_account_number ()
    {
        return bank_account_number;
    }

    public void setBank_account_number (String bank_account_number)
    {
        this.bank_account_number = bank_account_number;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public Wallet getWallet ()
    {
        return wallet;
    }

    public void setWallet (Wallet wallet)
    {
        this.wallet = wallet;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getNom ()
    {
        return nom;
    }

    public void setNom (String nom)
    {
        this.nom = nom;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [bank_account_number = "+bank_account_number+", address = "+address+", wallet = "+wallet+", phone = "+phone+", nom = "+nom+"]";
    }
}