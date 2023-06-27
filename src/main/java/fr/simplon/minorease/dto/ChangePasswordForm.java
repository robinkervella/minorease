package fr.simplon.minorease.dto;

import jakarta.validation.constraints.NotNull;

public class ChangePasswordForm
{
    private String username;
    private String oldPassword;
    @NotNull
    private String newPassword;
    @NotNull
    private String confirmPassword;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String pUsername)
    {
        username = pUsername;
    }

    public String getOldPassword()
    {
        return oldPassword;
    }

    public void setOldPassword(String pOldPassword)
    {
        oldPassword = pOldPassword;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String pNewPassword)
    {
        newPassword = pNewPassword;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    public void setConfirmPassword(String pConfirmPassword)
    {
        confirmPassword = pConfirmPassword;
    }
}