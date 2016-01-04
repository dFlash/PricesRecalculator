package net.mpopov.ci.common;

public abstract class ContextAdapter
{
    @SuppressWarnings("unchecked")
    protected <T> T getBean(String name)
    {
        return (T) Context.getInstance().getBean(name);
    }
}
