package tech.hsecit.project.crypto.retrofit_api.databean;


public class Wallet
{
    public Pair_pub_addr pair_pub_addr;

    public Private_key private_key;

    public Pair_pub_addr getPair_pub_addr ()
    {
        return pair_pub_addr;
    }

    public void setPair_pub_addr (Pair_pub_addr pair_pub_addr)
    {
        this.pair_pub_addr = pair_pub_addr;
    }

    public Private_key getPrivate_key ()
    {
        return private_key;
    }

    public void setPrivate_key (Private_key private_key)
    {
        this.private_key = private_key;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pair_pub_addr = "+pair_pub_addr+", private_key = "+private_key+"]";
    }
}
