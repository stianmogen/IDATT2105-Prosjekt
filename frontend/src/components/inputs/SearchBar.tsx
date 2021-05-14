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
import Select from 'components/inputs/Select';
import Paper from 'components/layout/Paper';
import SubmitButton from 'components/inputs/SubmitButton';

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
    padding: theme.spacing(1),
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
  mapContainerStyle: {
    width: '100%',
    height: theme.breakpoints.values.sm,
    borderRadius: theme.shape.borderRadius,
    [theme.breakpoints.down('md')]: {
      height: 300,
    },
  },
  mapFilter: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    marginTop: theme.spacing(1),
    marginBottom: theme.spacing(1),
  },
  divider: {
    height: 60,
    marginTop: -6,
    marginBottom: -6,
  },
  selected: {
    display: 'grid',
    gap: 0,
    maxHeight: 50,
  },
}));

type FormValues = {
  building?: string;
  date?: Date;
  time?: number;
  availableSpots?: number;
};
const SearchBar = () => {
  const classes = useStyles();
  const [open, setOpen] = useState(false);
  const { control, formState } = useForm<FormValues>();
  const resetFilters = async () => {
    setOpen(false);
  };
  return (
    <Paper className={classes.paper} noPadding>
      <form>
        <div className={classes.root}>
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => setOpen((prev) => !prev)} variant='text'>
            <div className={classes.selected}>
              <sup>Select Building</sup>
              <small>All Buildings</small>
            </div>
            <ArrowDropDownIcon />
          </Button>
          <Divider className={classes.divider} orientation='vertical' />
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => setOpen((prev) => !prev)} variant='text'>
            <div className={classes.selected}>
              <sup>Select Date</sup>
              <small>Any Date</small>
            </div>
            <ArrowDropDownIcon />
          </Button>
          <Divider className={classes.divider} orientation='vertical' />
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => setOpen((prev) => !prev)} variant='text'>
            <div className={classes.selected}>
              <sup>Select Time</sup>
              <small>0 Hours</small>
            </div>
            <ArrowDropDownIcon />
          </Button>
          <Divider className={classes.divider} orientation='vertical' />
          <Button aria-label='menu' className={classes.iconButton} fullWidth onClick={() => setOpen((prev) => !prev)} variant='text'>
            <div className={classes.selected}>
              <sup>Select Amount</sup>
              <small>0 Spots</small>
            </div>
            <ArrowDropDownIcon />
          </Button>
          <Divider className={classes.divider} orientation='vertical' />
          <IconButton className={classes.iconButton} type='submit'>
            <SearchIcon />
          </IconButton>
        </div>
        <Collapse in={open}>
          <Divider />
          <div className={classes.filterPaper}>
            <Typography variant='h3'>Filtre</Typography>
            <Select control={control} formState={formState} label='Trenings-nivå' name='level'>
              <MenuItem>Test 1</MenuItem>
            </Select>
            <div className={classes.grid}>
              <Button onClick={resetFilters} variant='outlined'>
                Nullstill filtre
              </Button>
              <SubmitButton formState={formState}>Aktiver filtre</SubmitButton>
            </div>
          </div>
        </Collapse>
      </form>
    </Paper>
  );
};

export default SearchBar;
