import { useState } from 'react';
import { useForm } from 'react-hook-form';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import MenuItem from '@material-ui/core/MenuItem';
import { Typography, Button, IconButton, Collapse, Divider } from '@material-ui/core';

// Icons
import SearchIcon from '@material-ui/icons/SearchRounded';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';

// Project components
import Paper from 'components/layout/Paper';

const useStyles = makeStyles((theme) => ({
  paper: {
    borderRadius: 20,
    padding: theme.spacing(0.25, 0.5),
    overflow: 'hidden',
    maxWidth: '60vw',
    margin: 'auto',
  },
  root: {
    display: 'flex',
    alignItems: 'center',
    width: '100%',
  },
  input: {
    marginLeft: theme.spacing(1),
    flex: 1,
  },
  iconButton: {
    display: 'flex',
    paddingLeft: theme.spacing(2),
    borderRadius: 0,
    color: theme.palette.text.primary,
  },
  filterPaper: {
    padding: theme.spacing(1),
    backgroundColor: theme.palette.background.paper,
  },
  level: {
    display: 'flex',
    flexDirection: 'column',
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr',
    gap: theme.spacing(1),
  },
  divider: {
    height: 60,
    marginTop: -6,
    marginBottom: -6,
  },
  selected: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gap: 0,
    maxHeight: 50,
  },
  icon: {
    marginLeft: 'auto',
  },
}));
const SearchBar = () => {
  const classes = useStyles();
  const [open, setOpen] = useState(false);
  const [open2, setOpen2] = useState(false);
  const [open3, setOpen3] = useState(false);
  const [open4, setOpen4] = useState(false);

  const closeAll = (id: number) => {
    if (setOpen && id !== 1) {
      setOpen(false);
    }
    if (setOpen2 && id !== 2) {
      setOpen2(false);
    }
    if (setOpen3 && id !== 3) {
      setOpen3(false);
    }
    if (setOpen4 && id !== 4) {
      setOpen4(false);
    }
  };

  const handleMenu = (menu: string) => {
    if (menu === '1') {
      closeAll(1);
      setOpen((prev) => !prev);
    }
    if (menu === '2') {
      closeAll(2);
      setOpen2((prev) => !prev);
    }
    if (menu === '3') {
      closeAll(3);
      setOpen3((prev) => !prev);
    }
    if (menu === '4') {
      closeAll(4);
      setOpen4((prev) => !prev);
    }
  };
  return (
    <Paper className={classes.paper} noPadding>
      <form>
        <div className={classes.root}>
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => handleMenu('1')} variant='text'>
            <div className={classes.selected}>
              <Typography variant='h5'>Select Building</Typography>
              <Typography variant='h4'>All Buildings</Typography>
            </div>
            <div className={classes.icon}>
              <ArrowDropDownIcon />
            </div>
          </Button>
          <Divider className={classes.divider} orientation='vertical' />
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => handleMenu('2')} variant='text'>
            <div className={classes.selected}>
              <Typography variant='h5'>Select Date</Typography>
              <Typography variant='h4'>Any Date</Typography>
            </div>
            <div className={classes.icon}>
              <ArrowDropDownIcon />
            </div>
          </Button>
          <Divider className={classes.divider} orientation='vertical' />
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => handleMenu('3')} variant='text'>
            <div className={classes.selected}>
              <Typography variant='h5'>Select Time</Typography>
              <Typography variant='h4'>0 Hours</Typography>
            </div>
            <div className={classes.icon}>
              <ArrowDropDownIcon />
            </div>
          </Button>
          <Divider className={classes.divider} orientation='vertical' />
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => handleMenu('4')} variant='text'>
            <div className={classes.selected}>
              <Typography variant='h5'>Select Amount</Typography>
              <Typography variant='h4'>0 Spots</Typography>
            </div>
            <div className={classes.icon}>
              <ArrowDropDownIcon />
            </div>
          </Button>
          <Divider className={classes.divider} orientation='vertical' />
          <IconButton className={classes.iconButton} type='submit'>
            <SearchIcon />
          </IconButton>
        </div>
        <Collapse in={open}>
          <Divider />
          <div className={classes.filterPaper} id='1'>
            <Typography variant='h3'>Meny 1</Typography>
          </div>
        </Collapse>
        <Collapse in={open2}>
          <Divider />
          <div className={classes.filterPaper} id='2'>
            <Typography variant='h3'>Meny 2</Typography>
          </div>
        </Collapse>
        <Collapse in={open3}>
          <Divider />
          <div className={classes.filterPaper} id='2'>
            <Typography variant='h3'>Meny 3</Typography>
          </div>
        </Collapse>
        <Collapse in={open4}>
          <Divider />
          <div className={classes.filterPaper} id='2'>
            <Typography variant='h3'>Meny 4</Typography>
          </div>
        </Collapse>
      </form>
    </Paper>
  );
};

export default SearchBar;
