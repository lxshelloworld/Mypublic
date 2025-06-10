import React from 'react';
import { Container, Box, useMediaQuery, useTheme } from '@mui/material';

const ResponsiveLayout = ({ children }) => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

  return (
    <Container maxWidth="sm">
      <Box
        mt={isMobile ? 4 : 10}
        p={isMobile ? 2 : 4}
        boxShadow={isMobile ? 0 : 3}
        borderRadius={isMobile ? 0 : 2}
        bgcolor="background.paper"
      >
        {children}
      </Box>
    </Container>
  );
};

export default ResponsiveLayout;
