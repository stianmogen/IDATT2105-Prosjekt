import { useState } from 'react';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import { Typography, Button, IconButton, Collapse, Divider, MenuItem } from '@material-ui/core';
import LocalizationProvider from '@material-ui/lab/LocalizationProvider/LocalizationProvider';
import AdapterDateFns from '@material-ui/lab/AdapterDateFns';

// Icons
import SearchIcon from '@material-ui/icons/SearchRounded';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';

// Project components
import Paper from 'components/layout/Paper';
import Bool from './Bool';
import { useForm } from 'react-hook-form';
import DatePicker from 'components/inputs/DatePicker';
import Select from 'components/inputs/Select';
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
  buildingSelector: {
    display: 'flex',
    justifyContent: 'left',
    alignItems: 'center',
  },
}));

type FormValues = {
  building1: boolean;
  building2: boolean;
  building3: boolean;
  date: Date;
  startTime: Date;
  endTime: Date;
  amount: number;
};

const SORT_OPTIONS = [
  { name: '1', key: '1' },
  { name: '2', key: '2' },
  { name: '3', key: '3' },
  { name: '4', key: '4' },
  { name: '5', key: '5' },
  { name: '6', key: '6' },
  { name: '7', key: '7' },
  { name: '8', key: '8' },
  { name: '9', key: '9' },
  { name: '10', key: '10' },
];
const SearchBar = () => {
  const classes = useStyles();
  const [open, setOpen] = useState(false);
  const [open2, setOpen2] = useState(false);
  const [open3, setOpen3] = useState(false);
  const [open4, setOpen4] = useState(false);
  const { control, formState } = useForm<FormValues>();
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

  //TODO: Make mobile version

  return (
    <Paper className={classes.paper} noPadding>
      <form>
        <div className={classes.root}>
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => handleMenu('1')} variant='text'>
            <div className={classes.selected}>
              <Typography variant='h5'>Select Building</Typography>
            </div>
            <div className={classes.icon}>
              <ArrowDropDownIcon />
            </div>
          </Button>
          <Divider className={classes.divider} orientation='vertical' />
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => handleMenu('2')} variant='text'>
            <div className={classes.selected}>
              <Typography variant='h5'>Select Date</Typography>
            </div>
            <div className={classes.icon}>
              <ArrowDropDownIcon />
            </div>
          </Button>
          <Divider className={classes.divider} orientation='vertical' />
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => handleMenu('3')} variant='text'>
            <div className={classes.selected}>
              <Typography variant='h5'>Select Time</Typography>
            </div>
            <div className={classes.icon}>
              <ArrowDropDownIcon />
            </div>
          </Button>
          <Divider className={classes.divider} orientation='vertical' />
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => handleMenu('4')} variant='text'>
            <div className={classes.selected}>
              <Typography variant='h5'>Select Amount</Typography>
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
            <div className={classes.buildingSelector}>
              <Bool control={control} formState={formState} name='building1' type='checkbox' />
              <Typography variant='h4'>Building 1</Typography>
            </div>
            <div className={classes.buildingSelector}>
              <Bool control={control} formState={formState} name='building2' type='checkbox' />
              <Typography variant='h4'>Building 2</Typography>
            </div>
            <div className={classes.buildingSelector}>
              <Bool control={control} formState={formState} name='building3' type='checkbox' />
              <Typography variant='h4'>Building 3</Typography>
            </div>
          </div>
        </Collapse>
        <Collapse in={open2}>
          <Divider />
          <div className={classes.filterPaper} id='2'>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <DatePicker control={control} formState={formState} fullWidth label='Date' margin='normal' name='date' type='date' />
            </LocalizationProvider>
          </div>
        </Collapse>
        <Collapse in={open3}>
          <Divider />
          <div className={classes.filterPaper} id='2'>
            <Typography variant='h3'>Meny 3</Typography>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <DatePicker control={control} formState={formState} fullWidth label='From' margin='normal' name='startTime' type='time' />
              <DatePicker control={control} formState={formState} fullWidth label='To' margin='normal' name='endTime' type='time' />
            </LocalizationProvider>
          </div>
        </Collapse>
        <Collapse in={open4}>
          <Divider />
          <div className={classes.filterPaper} id='2'>
            <Typography variant='h3'>Meny 4</Typography>
            <Select control={control} defaultValue={SORT_OPTIONS[0].key} formState={formState} label='Amount' margin='dense' name='amount'>
              {SORT_OPTIONS.map((value) => (
                <MenuItem key={value.key} value={value.key}>
                  {value.name}
                </MenuItem>
              ))}
            </Select>
          </div>
        </Collapse>
      </form>
    </Paper>
  );
};

export default SearchBar;
