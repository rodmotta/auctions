export const getKeycloakLoginUrl = () => {
  const clientId = "auction-app";
  const redirectUri = "http://localhost:5173/callback";
  const realm = "auction-realm";
  const keycloakBaseUri = "http://localhost:8000";

  const authUrl = `${keycloakBaseUri}/realms/${realm}/protocol/openid-connect/auth` +
    `?client_id=${clientId}` +
    `&response_type=code` +
    `&redirect_uri=${redirectUri}` +
    `&scope=openid` +
    `&prompt=login`;

  window.location.href = authUrl;
};