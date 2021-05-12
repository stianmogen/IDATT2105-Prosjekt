import { useState } from 'react';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';

// Icons
import LightIcon from '@material-ui/icons/WbSunnyRounded';

// Project components
import ThemeSettings from 'components/miscellaneous/ThemeSettings';
import Logo from 'components/miscellaneous/Logo';

const useStyles = makeStyles((theme) => ({
  content: {
    width: '100%',
    padding: theme.spacing(5),
    display: 'grid',
    gap: theme.spacing(2),
    gridTemplateColumns: '1fr 1fr',
    color: theme.palette.text.primary,
    maxWidth: theme.breakpoints.values.md,
    margin: 'auto',

    [theme.breakpoints.down('lg')]: {
      padding: theme.spacing(4),
      gap: theme.spacing(1),
      gridTemplateColumns: '1fr',
    },
  },
  link: {
    margin: 'auto',
  },
  logo: {
    minWidth: '250px',
    width: '46%',
    maxWidth: '100%',
    height: 'auto',
    [theme.breakpoints.down('md')]: {
      minWidth: '200px',
    },
  },
  theme: {
    display: 'flex',
    justifyContent: 'center',
  },
}));

const Footer = () => {
  const classes = useStyles();
  const [showThemeSettings, setShowThemeSettings] = useState(false);

  return (
    <footer>
      <Divider variant='middle' />
      <div className={classes.content}>
        <Logo className={classes.logo} darkColor='white' lightColor='white' size='large' />
        <div className={classes.theme}>
          <IconButton aria-label='delete' onClick={() => setShowThemeSettings(true)}>
            <LightIcon />
          </IconButton>
          <ThemeSettings onClose={() => setShowThemeSettings(false)} open={showThemeSettings} />
        </div>
      </div>
    </footer>
  );
};

export default Footer;
